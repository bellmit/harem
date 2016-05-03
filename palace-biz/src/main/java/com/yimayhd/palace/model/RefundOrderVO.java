package com.yimayhd.palace.model;

import com.yimayhd.palace.model.enums.AfterSaleAuditStatus;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.refund.client.domain.RefundOperationRecordDO;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author create by yushengwei on 2016/3/30
 * @Description
 */
public class RefundOrderVO implements Serializable{
    String signId;
    RefundOrderDO refundOrderDO;
    OrderDetails orderDetails;
    //审核状态，审核通过，审核不通过，初审审核通过，初审审核不通过，收货审核通过，收货审核不通过
    AfterSaleAuditStatus afterSaleAuditStatus;

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

    public AfterSaleAuditStatus getAfterSaleAuditStatus() {
        //从refundOrderDO 的List<RefundOperationRecordDO> 取出审核记录，并更新成最新的数据
        List<RefundOperationRecordDO> list = refundOrderDO.getRefundOperationRecordDOList();
        if(null != refundOrderDO && CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(AfterSaleAuditStatus.getAllStatus())){
            int refundStatus = 0;
            for (RefundOperationRecordDO ed:list) {
                if(AfterSaleAuditStatus.getAllStatus().contains(ed.getRefundStatus()) ){//只看审核的
                    if(ed.getRefundStatus() > refundStatus){
                        refundStatus = ed.getRefundStatus();
                        afterSaleAuditStatus = AfterSaleAuditStatus.getAfterSaleAuditStatus(refundStatus,refundOrderDO.getBizType());
                    }
                    return afterSaleAuditStatus;
                }
            }
        }
        return afterSaleAuditStatus;
    }

    public void setAfterSaleAuditStatus(AfterSaleAuditStatus afterSaleAuditStatus) {
        this.afterSaleAuditStatus = getAfterSaleAuditStatus();
    }
}
