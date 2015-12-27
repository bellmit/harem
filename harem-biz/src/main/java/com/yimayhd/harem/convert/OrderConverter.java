package com.yimayhd.harem.convert;

import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.enums.OrderActionStatus;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.trade.MainOrder;
import com.yimayhd.harem.model.trade.OrderDetails;
import com.yimayhd.harem.model.trade.SubOrder;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.*;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/18.
 *
 */
public class OrderConverter {
    private static final Logger LOG = LoggerFactory.getLogger(OrderConverter.class);

    public static OrderQueryDTO orderListQueryToOrderQueryDTO(OrderListQuery orderListQuery,long userId){
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        orderQueryDTO.setPageNo(orderListQuery.getPageNumber());
        orderQueryDTO.setPageSize(orderListQuery.getPageSize());
        //订单类型

        int [] orderBizTypes = {orderListQuery.getOrderType()};
        orderQueryDTO.setOrderBizTypes(orderBizTypes);

        //订单编号
        if (StringUtils.isNotEmpty(orderListQuery.getOrderNO()) ){
            if (NumberUtils.isDigits(orderListQuery.getOrderNO())){
                List<Long> bizOrderIds = new ArrayList<Long>();
                bizOrderIds.add(NumberUtils.toLong(orderListQuery.getOrderNO()));
                orderQueryDTO.setBizOrderIds(bizOrderIds);
            }else{
                return null;
            }
        }
        //下单开始日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate()));
            } catch (ParseException e) {
                LOG.error("orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }else{
            //TODO 测试需要注释。测试完打开代码（默认取今天的所有数据）
//            String date = DateUtil.date2StringByDay(new Date());
//            try{
//                orderQueryDTO.setStartDate(DateUtil.convertStringToDate(date));
//            }catch (ParseException e){
//                LOG.error("orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate())); Exception:" + e);
//                e.printStackTrace();
//            }
        }
        //下单结束日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate()));
            } catch (ParseException e) {
                LOG.error("orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
        //订单状态
        String orderState = orderListQuery.getOrderStat();
        if (StringUtils.isNotEmpty(orderState)){
            if (orderState.equals(PayStatus.NOT_PAY.toString())){
                int [] payStatus = {PayStatus.NOT_PAY.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
            }else if (orderState.equals(LogisticsStatus.NO_LG_ORDER.toString())){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.NO_LG_ORDER.getStatus(),LogisticsStatus.UNCONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(LogisticsStatus.CONSIGNED.toString())){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.CONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.SUCCESS.toString())){
                int [] payStatus = {PayStatus.SUCCESS.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.DELIVERED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.NOT_PAY_CLOSE.toString())){
                int [] payStatus = {PayStatus.NOT_PAY_CLOSE.getStatus(),PayStatus.REFUNDED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
            }
        }
        //买家手机号，买家昵称
        if (userId >0){
            orderQueryDTO.setBuyerId(userId);
        }
        //名称
        if (StringUtils.isNotEmpty(orderListQuery.getItemName())){
            orderQueryDTO.setItemName(orderListQuery.getItemName());
            orderQueryDTO.setIsMain(MainDetailStatus.NO.getType());
            orderQueryDTO.setIsDetail(MainDetailStatus.YES.getType());
        }else{
            orderQueryDTO.setIsMain(MainDetailStatus.YES.getType());
            orderQueryDTO.setNeedDetailOrders(true);
        }
        return orderQueryDTO;
    }

    /**
     * 此方法判断的我蛋疼。估计过段时间只有神才知道如何判断了。
     */
    public static MainOrder mainOrderStatusConverter(MainOrder mainOrder,BizOrderDO bizOrderDO) {
        int payStatus = bizOrderDO.getPayStatus();
        int logisticsStatus = bizOrderDO.getLogisticsStatus();
        int refundStatus = bizOrderDO.getRefundStatus();
        mainOrder.setOrderActionStates(OrderActionStatus.NOTING.getStatus());
        if (bizOrderDO.getBizType() == OrderBizType.LINE.getBizType()){
            if (payStatus == PayStatus.NOT_PAY.getStatus()){
                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
            }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.RETURNED.getStatus() != logisticsStatus ){
                mainOrder.setOrderActionStates(OrderActionStatus.AFFIRM_CANCEL.getStatus());
            }else if (LogisticsStatus.CONSIGNED.getStatus() == payStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.FINISH_CANCEL.getStatus());
            }else if (refundStatus>0){
                if(RefundStatus.REFUND_SUCCESS.getStatus() != refundStatus){
                    mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
                }
            }
        }else if (bizOrderDO.getBizType() == OrderBizType.SPOTS.getBizType()){
            if (payStatus == PayStatus.NOT_PAY.getStatus()){
                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
            }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.RETURNED.getStatus() != logisticsStatus ){
                mainOrder.setOrderActionStates(OrderActionStatus.CONSIGN_CANCEL.getStatus());
            }else if (refundStatus>0){
                if(RefundStatus.REFUND_SUCCESS.getStatus() != refundStatus){
                    mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
                }
            }else if (PayStatus.REFUNDED.getStatus() != payStatus && PayStatus.NOT_PAY_CLOSE.getStatus() != payStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
            }
        }else if (bizOrderDO.getBizType() == OrderBizType.HOTEL.getBizType()){
            if (payStatus == PayStatus.NOT_PAY.getStatus()){
                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
            }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.RETURNED.getStatus() != logisticsStatus ){
                mainOrder.setOrderActionStates(OrderActionStatus.AFFIRM_CANCEL.getStatus());
            }else if (refundStatus>0){
                if(RefundStatus.REFUND_SUCCESS.getStatus() != refundStatus){
                    mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
                }
            }else if (PayStatus.REFUNDED.getStatus() != payStatus && PayStatus.NOT_PAY_CLOSE.getStatus() != payStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
            }
        }else if (bizOrderDO.getBizType() == OrderBizType.NORMAL.getBizType()){
            if (payStatus == PayStatus.NOT_PAY.getStatus()){
                mainOrder.setOrderActionStates(OrderActionStatus.UPDATE_ADDRESS_CANCEL.getStatus());
            }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.RETURNED.getStatus() != logisticsStatus ){
                mainOrder.setOrderActionStates(OrderActionStatus.CONSIGN_CANCEL.getStatus());
            }else if (LogisticsStatus.CONSIGNED.getStatus() == logisticsStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.OVERTIME.getStatus());
            }else if (refundStatus>0){
                if(RefundStatus.REFUND_SUCCESS.getStatus() != refundStatus){
                    mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
                }
            }else if (PayStatus.REFUNDED.getStatus() != payStatus && PayStatus.NOT_PAY_CLOSE.getStatus() != payStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
            }
        }else if (bizOrderDO.getBizType() == OrderBizType.ACTIVITY.getBizType()){
            if (payStatus == PayStatus.NOT_PAY.getStatus()){
                mainOrder.setOrderActionStates(OrderActionStatus.CANCEL.getStatus());
            }else if (PayStatus.PAID.getStatus() == payStatus && LogisticsStatus.RETURNED.getStatus() != logisticsStatus ){
                mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
            }else if (refundStatus>0){
                if(RefundStatus.REFUND_SUCCESS.getStatus() != refundStatus){
                    mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
                }
            }else if (PayStatus.REFUNDED.getStatus() != payStatus && PayStatus.NOT_PAY_CLOSE.getStatus() != payStatus){
                mainOrder.setOrderActionStates(OrderActionStatus.REFUND.getStatus());
            }
        }
        return mainOrder;
    }


    public static MainOrder orderVOConverter(BizOrderDO bizOrderDO) {
        if(bizOrderDO!=null){
            if (BizOrderUtil.hasDetailOrder(bizOrderDO)) {
                List<SubOrder> subOrderList = new ArrayList<SubOrder>();
                if (!CollectionUtils.isEmpty(bizOrderDO.getDetailOrderList())){
                    for (BizOrderDO detailOrder : bizOrderDO.getDetailOrderList()) {
                        SubOrder subOrder =  new SubOrder();
                        subOrder.setBizOrderDO(detailOrder);
                        if (bizOrderDO.getBizType() == OrderBizType.LINE.getBizType()){
                            long departDate = BizOrderUtil.getLineDepartDate(detailOrder);
                            subOrder.setExecuteTime(departDate);//出发时间
                        }else if (bizOrderDO.getBizType() == OrderBizType.SPOTS.getBizType()){
                            long spotStartDate = BizOrderUtil.getSpotStartDate(detailOrder);
                            subOrder.setExecuteTime(spotStartDate);//入院时间
                        }else if (bizOrderDO.getBizType() == OrderBizType.HOTEL.getBizType()){
                            long hotelStartDate = BizOrderUtil.getHotelStartDate(detailOrder);
                            long hotelEndDate = BizOrderUtil.getHotelEndDate(detailOrder);
                            subOrder.setStartTime(hotelStartDate);//入住日期
                            subOrder.setEndTime(hotelEndDate);//离店日期
                        }
                        subOrderList.add(subOrder);
                    }
                    return new MainOrder(bizOrderDO,subOrderList);
                }else{
                    subOrderList.add(new SubOrder(bizOrderDO));
                    return new MainOrder(bizOrderDO,subOrderList);
                }
            } else {
                List<SubOrder> subOrderList = new ArrayList<SubOrder>();
                subOrderList.add(new SubOrder(bizOrderDO));
                return new MainOrder(bizOrderDO,subOrderList);
            }
        }
        return null;
    }


}
