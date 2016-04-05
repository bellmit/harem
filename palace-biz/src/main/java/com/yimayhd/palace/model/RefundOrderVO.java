package com.yimayhd.palace.model;

import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;

import java.io.Serializable;

/**
 * @author create by yushengwei on 2016/3/30
 * @Description
 */
public class RefundOrderVO implements Serializable{
    String signId;
    RefundOrderDO refundOrderDO;
    OrderDetails orderDetails;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public RefundOrderDO getRefundOrderDO() {
        return refundOrderDO;
    }

    public void setRefundOrderDO(RefundOrderDO refundOrderDO) {
        this.refundOrderDO = refundOrderDO;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

}
