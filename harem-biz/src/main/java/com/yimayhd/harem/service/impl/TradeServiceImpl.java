package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.model.BizOrderVO;
import com.yimayhd.harem.service.TradeService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.harem.util.excel.JxlFor2003;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.query.PayOrderQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.service.QueryPayOrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TradeServiceImpl implements TradeService {
    @Autowired
    private QueryPayOrderService QueryPayOrderServiceRef;
    @Autowired
    private TcQueryService tcQueryServiceRef;
    @Autowired
    private UserService userServiceRef;
    @Override
    public PageVO<BizOrderVO> getOrderList(TradeListQuery tradeListQuery) throws Exception{
        //查询条件对接
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        //TODO 商家ID
        orderQueryDTO.setSellerId((long) 1);
        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null == tradeListQuery.getBizOrderId()) {
            List<Long> bizOrderIds = new ArrayList<Long>();
            bizOrderIds.add(tradeListQuery.getBizOrderId());
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setPageNo(tradeListQuery.getPageNumber());
        orderQueryDTO.setPageSize(tradeListQuery.getPageSize());
        //调用接口
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);
        PageVO<BizOrderVO> pageVO = null;
        List<BizOrderVO> bizOrderVOList = new ArrayList<BizOrderVO>();
        if(null != batchQueryResult && batchQueryResult.isSuccess()) {
            //查会员
            List<Long> userIdList = new ArrayList<Long>();
            for (BizOrderDO bizOrderDO : batchQueryResult.getBizOrderDOList()){
                userIdList.add(bizOrderDO.getBuyerId());
            }
            List<UserDO> userDOList = userServiceRef.getUserInfoList(userIdList);
            Map<Long,UserDO> userMap = new HashMap<Long, UserDO>();
            for (UserDO userDO : userDOList){
                userMap.put(userDO.getId(),userDO);
            }
            for (BizOrderDO bizOrderDO : batchQueryResult.getBizOrderDOList()){
                BizOrderVO bizOrderVO = BizOrderVO.getBizOrderVO(bizOrderDO);
                //获取部门工号，中短号等信息
                bizOrderVO.setMallInfo(BizOrderUtil.getIMallInfo(bizOrderDO));
                //TODO 给order加上买家信息对象
                //bizOrderVO.setBuyer(userMap.get(bizOrderDO.getBuyerId()));
                bizOrderVOList.add(bizOrderVO);
            }
            pageVO = new PageVO<BizOrderVO>(tradeListQuery.getPageNumber(), tradeListQuery.getPageSize(), (int) batchQueryResult.getTotalCount(), bizOrderVOList);
        }
        return pageVO;
    }

    @Override
    public void exportOrderList(HttpServletResponse response, TradeListQuery tradeListQuery) throws Exception {
        //查询条件对接
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        //TODO 商家ID
        orderQueryDTO.setSellerId((long) 1);
        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null == tradeListQuery.getBizOrderId()) {
            List<Long> bizOrderIds = new ArrayList<Long>();
            bizOrderIds.add(tradeListQuery.getBizOrderId());
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setPageNo(tradeListQuery.getPageNumber());
        orderQueryDTO.setPageSize(tradeListQuery.getPageSize());
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);
        PageVO<BizOrderDO> pageVO = null;
        if(null != batchQueryResult && batchQueryResult.isSuccess()) {
            //TODO
        }else{
            //返回的结果错误
            throw new BaseException(batchQueryResult);
        }
    }

    @Override
    public PageVO<PayOrderDO> getPayOrderList(PayListQuery payListQuery)throws Exception{
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        if(null != payListQuery.getBizOrderId()) {
            payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        }
        //TODO SessionUtil商家ID
        payOrderQuery.setSellerId(10000000);
        payOrderQuery.setPageSize(payListQuery.getPageSize());
        payOrderQuery.setPageNo(payListQuery.getPageNumber());
        if(!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        PageVO<PayOrderDO> pageVO = null;
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            pageVO = new PageVO<PayOrderDO>(payListQuery.getPageSize(),payPageResultDTO.getTotalCount(),payPageResultDTO.getPageNo(),payPageResultDTO.getList());
        }

        return pageVO;
    }

    @Override
    public void exportPayOrderList(HttpServletResponse response,PayListQuery payListQuery) throws Exception {
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        //TODO SessionUtil商家ID
        payOrderQuery.setSellerId(10000000);
        //导出10000条
        payOrderQuery.setPageSize(10000);
        if(!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if (!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
            headList.add(new BasicNameValuePair("tradeNo", "交易号"));
            headList.add(new BasicNameValuePair("id", "商户订单号"));
            headList.add(new BasicNameValuePair("subject", "商品信息"));
            headList.add(new BasicNameValuePair("buyerAccount", "对方账号"));
            headList.add(new BasicNameValuePair("totalAmount", "交易金额"));
            headList.add(new BasicNameValuePair("payStatus", "状态"));
            JxlFor2003.exportExcel(response, "payList.xls", payPageResultDTO.getList(), headList);

        }

    }

    @Override
    public List<BizOrderDO> getOrderByOrderId(long orderId) throws Exception {
        OrderQueryOption orderQueryOption = new OrderQueryOption();
        orderQueryOption.setAll();
        SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(orderId,orderQueryOption);
        //返回数组
        List<BizOrderDO> bizOrderDOList = new ArrayList<BizOrderDO>();
        if(singleQueryResult.isSuccess()) {
            //判断是否有自订单
            if (BizOrderUtil.hasDetailOrder(singleQueryResult.getBizOrderDO())) {
                bizOrderDOList = singleQueryResult.getBizOrderDO().getDetailOrderList();
            } else {
                bizOrderDOList.add(singleQueryResult.getBizOrderDO());
            }
        }
        return bizOrderDOList;
    }
}
