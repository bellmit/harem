package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.BizOrderExportVO;
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
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.collections.CollectionUtils;
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
    private static final int ORDER_IS_MAIN = 1;//主订单
    private static final int EXPORT_PAGE_NUMBER = 1;//导出数据时的页码
    private static final int EXPORT_PAGE_SIZE = 100000;//导出数据的条数上限
    @Autowired
    private QueryPayOrderService QueryPayOrderServiceRef;
    @Autowired
    private TcQueryService tcQueryServiceRef;
    @Autowired
    private UserService userServiceRef;
    @Override
    public PageVO<BizOrderVO> getOrderList(long sellerId,TradeListQuery tradeListQuery) throws Exception{
        //返回结果
        PageVO<BizOrderVO> pageVO = new PageVO<BizOrderVO>(tradeListQuery.getPageNumber(),tradeListQuery.getPageSize(),0);
        //查询条件对接
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        orderQueryDTO.setSellerId(sellerId);
        if(!StringUtils.isBlank(tradeListQuery.getPhone())){
            //根据手机号查询用户
            BaseResult<UserDO> baseResult = userServiceRef.getUserDOByMobile(tradeListQuery.getPhone());
            if(null != baseResult || baseResult.isSuccess()){
                //用户不存在的话直接返回空记录
                return pageVO;
            }
            orderQueryDTO.setBuyerId(baseResult.getValue().getId());
        }

        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null == tradeListQuery.getBizOrderId()) {
            List<Long> bizOrderIds = new ArrayList<Long>();
            bizOrderIds.add(tradeListQuery.getBizOrderId());
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(tradeListQuery.getEndDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setNeedExtFeature(true);
        orderQueryDTO.setIsMain(ORDER_IS_MAIN);
        orderQueryDTO.setPageNo(tradeListQuery.getPageNumber());
        orderQueryDTO.setPageSize(tradeListQuery.getPageSize());



        //调用接口
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);

        List<BizOrderVO> bizOrderVOList = new ArrayList<BizOrderVO>();
        if(null != batchQueryResult && batchQueryResult.isSuccess() && CollectionUtils.isNotEmpty(batchQueryResult.getBizOrderDOList())) {
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
                //给order加上买家信息对象
                //bizOrderVO.setBuyer(userMap.get(bizOrderDO.getBuyerId()));
                bizOrderVOList.add(bizOrderVO);
            }
            pageVO = new PageVO<BizOrderVO>(tradeListQuery.getPageNumber(), tradeListQuery.getPageSize(), (int) batchQueryResult.getTotalCount(), bizOrderVOList);
        }
        return pageVO;
    }

    @Override
    public List<BizOrderExportVO> exportOrderList(long sellerId, TradeListQuery tradeListQuery) throws Exception {
        //返回结果
        List<BizOrderExportVO> bizOrderExportVOList = null;
        //查询条件对接
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        orderQueryDTO.setSellerId(sellerId);
        if(!StringUtils.isBlank(tradeListQuery.getPhone())){
            //根据手机号查询用户
            BaseResult<UserDO> baseResult = userServiceRef.getUserDOByMobile(tradeListQuery.getPhone());
            if(null != baseResult || baseResult.isSuccess()){
                //用户不存在的话直接返回空记录
                return bizOrderExportVOList;
            }
            orderQueryDTO.setBuyerId(baseResult.getValue().getId());
        }

        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null == tradeListQuery.getBizOrderId()) {
            List<Long> bizOrderIds = new ArrayList<Long>();
            bizOrderIds.add(tradeListQuery.getBizOrderId());
        }
        if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(tradeListQuery.getEndDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setNeedExtFeature(true);
        orderQueryDTO.setIsMain(ORDER_IS_MAIN);
        orderQueryDTO.setPageNo(EXPORT_PAGE_NUMBER);
        orderQueryDTO.setPageSize(EXPORT_PAGE_SIZE);
        //调用接口
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);

        if(null != batchQueryResult && batchQueryResult.isSuccess() && CollectionUtils.isNotEmpty(batchQueryResult.getBizOrderDOList())) {
            for (BizOrderDO bizOrderDO : batchQueryResult.getBizOrderDOList()){
                bizOrderExportVOList.add(BizOrderExportVO.getBizOrderExportVO(bizOrderDO));
            }
        }
        return bizOrderExportVOList;
    }

    @Override
    public PageVO<PayOrderDO> getPayOrderList(long sellerId,PayListQuery payListQuery)throws Exception{
        PageVO<PayOrderDO> pageVO = new PageVO<PayOrderDO>(payListQuery.getPageNumber(),payListQuery.getPageSize(),0);
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setSellerId(sellerId);
        if(null != payListQuery.getBizOrderId()) {
            payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        }

        payOrderQuery.setPageSize(payListQuery.getPageSize());
        payOrderQuery.setPageNo(payListQuery.getPageNumber());
        if(!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(payListQuery.getEndDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            pageVO = new PageVO<PayOrderDO>(payListQuery.getPageSize(),payPageResultDTO.getTotalCount(),payPageResultDTO.getPageNo(),payPageResultDTO.getList());
        }

        return pageVO;
    }

    @Override
    public List<PayOrderDO> exportPayOrderList(long sellerId,PayListQuery payListQuery) throws Exception {
        List<PayOrderDO> payOrderDOList = new ArrayList<PayOrderDO>();
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setSellerId(sellerId);
        if(null != payListQuery.getBizOrderId()) {
            payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        }

        //导出10000条
        payOrderQuery.setPageSize(EXPORT_PAGE_SIZE);
        payOrderQuery.setPageNo(EXPORT_PAGE_NUMBER);
        if(!StringUtils.isBlank(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if (!StringUtils.isBlank(payListQuery.getEndDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            payOrderDOList = payPageResultDTO.getList();
        }
        return payOrderDOList;

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
