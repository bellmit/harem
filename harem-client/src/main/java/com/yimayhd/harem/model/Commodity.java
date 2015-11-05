package com.yimayhd.harem.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Commodity {
    private Long orderId;//订单ID
    private String name;//商品名称
    private BigDecimal price;//单价
    private Long number;//数量

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
