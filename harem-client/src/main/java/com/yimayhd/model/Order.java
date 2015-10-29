package com.yimayhd.model;

import com.yimayhd.base.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Order extends BaseModel {
    private String orderNO;//订单编号
    private long orderId;//貌似有问题
    private List<Commodity> commodityList;


    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }
}
