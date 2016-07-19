package com.yimayhd.palace.model.query;

import java.io.Serializable;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出excel的查询条件
 */
public class ExportQuery implements Serializable{
    private String orderStat;//订单状态
    private String buyerPhone;//买家手机号
    private String buyerName;//买家昵称
    private String itemName;//名称
    private String beginDate;
    private String endDate;
    private int [] orderTypes;//订单类型
    private Integer domain;
    private Set<Integer> ids;

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int[] getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(int[] orderTypes) {
        this.orderTypes = orderTypes;
    }

    public Integer getDomain() {
        return domain;
    }

    public void setDomain(Integer domain) {
        this.domain = domain;
    }

    public Set<Integer> getIds() {
        return ids;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }
}
