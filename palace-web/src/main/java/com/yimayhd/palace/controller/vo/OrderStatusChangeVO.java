package com.yimayhd.palace.controller.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeVO implements Serializable {

    private static final long serialVersionUID = -2531177081102535848L;

    private String bizOrderIdStr;

    private List<Long> bizOrderIds;

    private int OrderChangeStatus; //订单修改状态

    private String desc;// 备注

    private long userId;//登录人userid


    public String getBizOrderIdStr() {
        return bizOrderIdStr;
    }

    public void setBizOrderIdStr(String bizOrderIdStr) {
        this.bizOrderIdStr = bizOrderIdStr;
    }

    public List<Long> getBizOrderIds() {
        return bizOrderIds;
    }

    public void setBizOrderIds(List<Long> bizOrderIds) {
        this.bizOrderIds = bizOrderIds;
    }

    public int getOrderChangeStatus() {
        return OrderChangeStatus;
    }

    public void setOrderChangeStatus(int orderChangeStatus) {
        OrderChangeStatus = orderChangeStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
