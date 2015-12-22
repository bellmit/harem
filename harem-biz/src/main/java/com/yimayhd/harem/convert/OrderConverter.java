package com.yimayhd.harem.convert;

import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.enums.PayStatus;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.LogisticsStatus;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/18.
 *
 */
public class OrderConverter {
    public static OrderQueryDTO orderListQueryToOrderQueryDTO(OrderListQuery orderListQuery,long userId){
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        orderQueryDTO.setPageNo(orderListQuery.getPageNumber());
        orderQueryDTO.setPageSize(orderListQuery.getPageSize());
        //订单类型

        int [] orderBizTypes = {orderListQuery.getOrderType()};
        orderQueryDTO.setOrderBizTypes(orderBizTypes);

        //订单编号
        if (StringUtils.isNotEmpty(orderListQuery.getOrderNO())){
            List<Long> bizOrderIds = new ArrayList<Long>();
            bizOrderIds.add(Long.parseLong(orderListQuery.getOrderNO()));
            orderQueryDTO.setBizOrderIds(bizOrderIds);
        }
        //下单开始日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setStartDate(DateUtil.convertStringToDate(orderListQuery.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //下单结束日期
        if (StringUtils.isNotEmpty(orderListQuery.getBeginDate())){
            try {
                orderQueryDTO.setEndDate(DateUtil.convertStringToDate(orderListQuery.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //订单状态
        String orderState = orderListQuery.getOrderStat();
        if (StringUtils.isNotEmpty(orderState)){
            if (orderState.equals(PayStatus.NOT_PAY)){
                int [] payStatus = {PayStatus.NOT_PAY.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
            }else if (orderState.equals(LogisticsStatus.NO_LG_ORDER)){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.NO_LG_ORDER.getStatus(),LogisticsStatus.UNCONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(LogisticsStatus.CONSIGNED)){
                int [] payStatus = {PayStatus.PAID.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.CONSIGNED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.SUCCESS)){
                int [] payStatus = {PayStatus.SUCCESS.getStatus()};
                int [] logisticsStatuses = {LogisticsStatus.DELIVERED.getStatus()};
                orderQueryDTO.setPayStatuses(payStatus);
                orderQueryDTO.setLogisticsStatuses(logisticsStatuses);
            }else if (orderState.equals(PayStatus.NOT_PAY_CLOSE)){
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


    public static List<Order> orderConverter(List<BizOrderDO> bizOrderDOList){
        List<Order> orderList = new ArrayList<Order>();
        for (BizOrderDO bizOrderDO : bizOrderDOList) {
            Order order = new Order();
//            order.setOrderNO();

            orderList.add(order);
        }
        return orderList;
    }
}
