package com.yimayhd.model.weChatPay;

import com.yimayhd.base.BaseModel;

import java.util.Date;

/**
 * 
 * @table order_no_wechat
 * @author 
 **/
public class OrderNoWechat extends BaseModel {

    private static final long serialVersionUID = 1L;


    private String id; // ID

    private String orderId; // ordersID或者orders_investID

    private Integer type; // 类型（0：茶叶订单ordersID；1：茶山投资订单ordersInvestID）

    private Date createdOn; // 创建时间

    private Date updatedOn; // 最后修改时间

    private Integer status; // 状态


    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setCreatedOn(Date createdOn){
        this.createdOn = createdOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setUpdatedOn(Date updatedOn){
        this.updatedOn = updatedOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}