package com.yimayhd.harem.model.trade;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class MainOrder {
    private BizOrderDO bizOrderDO;

    private List<SubOrder> subOrderList;

    private int orderActionStates;

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public List<SubOrder> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<SubOrder> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public int getOrderActionStates() {
        return orderActionStates;
    }

    public void setOrderActionStates(int orderActionStates) {
        this.orderActionStates = orderActionStates;
    }

    public MainOrder(BizOrderDO bizOrderDO, List<SubOrder> subOrderList) {
        this.bizOrderDO = bizOrderDO;
        this.subOrderList = subOrderList;
    }
}
