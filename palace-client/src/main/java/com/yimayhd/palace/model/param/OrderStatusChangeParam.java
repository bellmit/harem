package com.yimayhd.palace.model.param;

import com.yimayhd.palace.util.ConvertUtil;
import com.yimayhd.sellerAdmin.client.enums.OrderOperationLogStatus;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeParam implements Serializable{

    private static final long serialVersionUID = 4517316377342549485L;
    private static final String PUT=";";
    private String bizOrderIdStr;
    private String formOrderIdStr;//form 表单提交ids
    private List<Long> bizOrderIds;

    private int OrderChangeStatus; //订单修改状态
    private String currOrderStatusStr;//'当前订单状态'
    private int    currOrderStatus;
    private String content;//'修改内容'

    private String desc;// 备注

    private long userId;//登录人userid

    private Integer orderStat;//订单状态

    public String getBizOrderIdStr() {
        return bizOrderIdStr;
    }

    public void setBizOrderIdStr(String bizOrderIdStr) {
        this.bizOrderIdStr = bizOrderIdStr;
        if(StringUtils.isNotBlank(bizOrderIdStr)){
            if(bizOrderIdStr.indexOf(PUT)!=-1){
                this.bizOrderIds = ConvertUtil.stringTolong(bizOrderIdStr.split(PUT));
            }else{
                if(StringUtils.isNumeric(bizOrderIdStr)){
                    this.bizOrderIds =new ArrayList<Long>();
                    bizOrderIds.add(Long.valueOf(bizOrderIdStr));
                }
            }
        }
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

    public String getFormOrderIdStr() {
        return formOrderIdStr;
    }

    public void setFormOrderIdStr(String formOrderIdStr) {
        this.formOrderIdStr = formOrderIdStr;


    }

    public String getCurrOrderStatusStr() {
        return currOrderStatusStr;
    }

    public void setCurrOrderStatusStr(String currOrderStatusStr) {
        this.currOrderStatusStr = currOrderStatusStr;
    }

    public int getCurrOrderStatus() {
        return currOrderStatus;
    }

    public void setCurrOrderStatus(int currOrderStatus) {
        this.currOrderStatus = currOrderStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(StringUtils.isNotBlank(currOrderStatusStr)&&OrderChangeStatus!=0){
            content = currOrderStatus+"-"+ OrderOperationLogStatus.getByType(OrderChangeStatus);
        }
        this.content = content;
    }

    public Integer getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(Integer orderStat) {
        this.orderStat = orderStat;
    }
}
