package com.yimayhd.palace.model.param;

import com.sun.tools.javac.util.Convert;
import com.yimayhd.palace.util.ConvertUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeParam implements Serializable{

    private static final long serialVersionUID = 4517316377342549485L;
    private String bizOrderIdStr;
    private String formOrderIdStr;//form 表单提交ids
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

    public String getFormOrderIdStr() {
        return formOrderIdStr;
    }

    public void setFormOrderIdStr(String formOrderIdStr) {
        this.formOrderIdStr = formOrderIdStr;
        if(StringUtils.isNotBlank(bizOrderIdStr)){

            this.bizOrderIds = ConvertUtil.stringTolong(bizOrderIdStr.split(";"));
        }
    }
}
