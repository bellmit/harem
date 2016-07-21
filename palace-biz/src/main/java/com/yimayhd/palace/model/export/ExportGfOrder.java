package com.yimayhd.palace.model.export;

import java.io.Serializable;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description
 */
public class ExportGfOrder implements Serializable{
    public String consigneeName="收货人姓名";//lgOrder.fullName //收货人姓名
    public long buyerId=11;//order.buyerId//买家ID
    public String buyerName="name";//order.buyerName //买家
    public long buyerPhoneNum=18710022494L;//$!order.buyerPhoneNum//电话
    public String contactAddress="北京长安街";//$!lgOrder.prov， $!lgOrder.city，  $!lgOrder.area，$!lgOrder.address，//详细地址
    public long bizOrderId=111L;//$!order.mainOrder.bizOrderDO.bizOrderId//订单号
    public String itemTitle="itemTitle";//$!subOrder.bizOrderDO.itemTitle//商品名称
    public long itemId=10911L;//$!subOrder.bizOrderDO.itemId//商品ID
    public long itemPrice=10000L;//$!subOrder.bizOrderDO.itemPrice //商品价格
    public long actualFee=1009;//$!subOrder.sumFee//实际支付金额
    public long buyAmount=1;//$!subOrder.bizOrderDO.buyAmount//商品数量
    public String createDate="2016-11-11";//$!order.mainOrder.bizOrderDO.gmtCreated//下单时间
    public long sumFee=10098;//$!subOrder.sumFee//订单总额
    public long freightFee=98;//运费
    public String paymentMode="收下" ;// //支付方式
    public String orderShowState="发货中";//$order.mainOrder.orderShowState//订单状态

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public long getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(long buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getActualFee() {
        return actualFee;
    }

    public void setActualFee(long actualFee) {
        this.actualFee = actualFee;
    }

    public long getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(long buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getSumFee() {
        return sumFee;
    }

    public void setSumFee(long sumFee) {
        this.sumFee = sumFee;
    }

    public long getFreightFee() {
        return freightFee;
    }

    public void setFreightFee(long freightFee) {
        this.freightFee = freightFee;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOrderShowState() {
        return orderShowState;
    }

    public void setOrderShowState(String orderShowState) {
        this.orderShowState = orderShowState;
    }
}
