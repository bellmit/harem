package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.BizOrderExportVO;
import com.yimayhd.harem.model.BizOrderVO;
import com.yimayhd.harem.model.PayOrderExportVO;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.service.TradeService;
import com.yimayhd.harem.util.DateUtil;
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
import org.springframework.beans.factory.annotation.Autowired;

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
        List<BizOrderVO> bizOrderVOList = new ArrayList<BizOrderVO>();


        if(null != tradeListQuery.getBizOrderId()&& tradeListQuery.getBizOrderId() > 0){
            //按交易id查询
            OrderQueryOption orderQueryOption =  new OrderQueryOption();
            orderQueryOption.setAll();
            SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(tradeListQuery.getBizOrderId(),orderQueryOption);
            //返回数组
            List<BizOrderDO> bizOrderDOList = new ArrayList<BizOrderDO>();
            if(null != singleQueryResult && singleQueryResult.isSuccess()) {
                BizOrderDO bizOrderDO = singleQueryResult.getBizOrderDO();
                if(null != bizOrderDO){
                    BizOrderVO bizOrderVO = BizOrderVO.getBizOrderVO(bizOrderDO);
                    //给order加上买家信息对象
                    bizOrderVOList.add(bizOrderVO);
                    pageVO = new PageVO<BizOrderVO>(tradeListQuery.getPageNumber(),tradeListQuery.getPageSize(),1,bizOrderVOList);
                    return pageVO;
                }

            }

        }else{

            //查询条件对接
            OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
            orderQueryDTO.setSellerId(sellerId);
            if(!StringUtils.isBlank(tradeListQuery.getPhone())){
                //根据手机号查询用户
                BaseResult<UserDO> baseResult = userServiceRef.getUserDOByMobile(tradeListQuery.getPhone());
                if(null == baseResult || !baseResult.isSuccess()){
                    //用户不存在的话直接返回空记录
                    return pageVO;
                }
                orderQueryDTO.setBuyerId(baseResult.getValue().getId());
            }
            //订单状态
            if(0 != tradeListQuery.getPayStatus()){
                orderQueryDTO.setPayStatuses(new int[]{tradeListQuery.getPayStatus()});
            }
            orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});

            if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
                orderQueryDTO.setStartDate(DateUtil.formatMinTimeForDate(tradeListQuery.getBeginDate()));
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

                    //给order加上买家信息对象
                    //bizOrderVO.setBuyer(userMap.get(bizOrderDO.getBuyerId()));
                    bizOrderVOList.add(bizOrderVO);
                }
                pageVO = new PageVO<BizOrderVO>(tradeListQuery.getPageNumber(), tradeListQuery.getPageSize(), (int) batchQueryResult.getTotalCount(), bizOrderVOList);
                return pageVO;
            }
        }
        return pageVO;
    }

    @Override
    public List<BizOrderExportVO> exportOrderList(long sellerId, TradeListQuery tradeListQuery) throws Exception {
        //返回结果
        List<BizOrderExportVO> bizOrderExportVOList = null;
        if(null != tradeListQuery.getBizOrderId()&& tradeListQuery.getBizOrderId() > 0){
            //按交易id查询
            SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(tradeListQuery.getBizOrderId(),new OrderQueryOption());
            //返回数组
            List<BizOrderDO> bizOrderDOList = new ArrayList<BizOrderDO>();
            if(null != singleQueryResult && singleQueryResult.isSuccess()) {
                BizOrderDO bizOrderDO = singleQueryResult.getBizOrderDO();
                if(null != bizOrderDO){
                    bizOrderExportVOList = new ArrayList<BizOrderExportVO>();
                    BizOrderExportVO bizOrderExportVO = BizOrderExportVO.getBizOrderExportVO(bizOrderDO);
                    //给order加上买家信息对象
                    bizOrderExportVOList.add(bizOrderExportVO);
                    return bizOrderExportVOList;
                }

            }

        }else{
            //查询条件对接
            OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
            orderQueryDTO.setSellerId(sellerId);
            if(!StringUtils.isBlank(tradeListQuery.getPhone())){
                //根据手机号查询用户
                BaseResult<UserDO> baseResult = userServiceRef.getUserDOByMobile(tradeListQuery.getPhone());
                if(null == baseResult || !baseResult.isSuccess()){
                    //用户不存在的话直接返回空记录
                    return bizOrderExportVOList;
                }
                orderQueryDTO.setBuyerId(baseResult.getValue().getId());
            }
            //订单状态
            if(0 != tradeListQuery.getPayStatus()){
                orderQueryDTO.setPayStatuses(new int[]{tradeListQuery.getPayStatus()});
            }
            orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
            if(!StringUtils.isBlank(tradeListQuery.getBeginDate())) {
                orderQueryDTO.setStartDate(DateUtil.formatMinTimeForDate(tradeListQuery.getBeginDate()));
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
                bizOrderExportVOList = new ArrayList<BizOrderExportVO>();
                for (BizOrderDO bizOrderDO : batchQueryResult.getBizOrderDOList()){
                    bizOrderExportVOList.add(BizOrderExportVO.getBizOrderExportVO(bizOrderDO));
                }
                return bizOrderExportVOList;
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
            payOrderQuery.setStartDate(DateUtil.formatMinTimeForDate(payListQuery.getBeginDate()));
        }
        if(!StringUtils.isBlank(payListQuery.getEndDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            if(CollectionUtils.isNotEmpty(payPageResultDTO.getList())) {
                pageVO = new PageVO<PayOrderDO>(payListQuery.getPageNumber(),payListQuery.getPageSize(),payPageResultDTO.getTotalCount(), payPageResultDTO.getList());
            }
        }

        return pageVO;
    }

    @Override
    public List<PayOrderExportVO> exportPayOrderList(long sellerId,PayListQuery payListQuery) throws Exception {
        List<PayOrderExportVO> payOrderExportVOList = null;
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
        if(null != payPageResultDTO && payPageResultDTO.isSuccess() && CollectionUtils.isNotEmpty(payPageResultDTO.getList())) {
            payOrderExportVOList = new ArrayList<PayOrderExportVO>();
            for (PayOrderDO payOrderDO : payPageResultDTO.getList()){
                payOrderExportVOList.add(PayOrderExportVO.getPayOrderExportVO(payOrderDO));
            }
        }
        return payOrderExportVOList;

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
