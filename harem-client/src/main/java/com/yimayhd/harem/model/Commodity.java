package com.yimayhd.harem.model;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Commodity {
    private long orderId;//订单ID
    private String name;//商品名称
    private double price;//单价
    private long number;//数量

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
