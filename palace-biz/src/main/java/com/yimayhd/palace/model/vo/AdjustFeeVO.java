package com.yimayhd.palace.model.vo;

import java.util.Date;

/**
 * @author create by yushengwei on 2016/8/5
 * @Description
 * @return $returns
 */
public class AdjustFeeVO {
    long oldPrice;
    long newPrice;
    String remark;
    Date adjustDate;
    long adjustUserId;
    String adjustUserName;

    //public AdjustFeeVO() {}

    public AdjustFeeVO(long oldPrice, long newPrice, String remark, Date adjustDate, long adjustUserId, String adjustUserName) {
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.remark = remark;
        this.adjustDate = adjustDate;
        this.adjustUserId = adjustUserId;
        this.adjustUserName = adjustUserName;
    }

    public long getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(long oldPrice) {
        this.oldPrice = oldPrice;
    }

    public long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(long newPrice) {
        this.newPrice = newPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    public long getAdjustUserId() {
        return adjustUserId;
    }

    public void setAdjustUserId(long adjustUserId) {
        this.adjustUserId = adjustUserId;
    }

    public String getAdjustUserName() {
        return adjustUserName;
    }

    public void setAdjustUserName(String adjustUserName) {
        this.adjustUserName = adjustUserName;
    }
}
