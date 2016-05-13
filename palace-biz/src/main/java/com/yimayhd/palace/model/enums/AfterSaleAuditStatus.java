package com.yimayhd.palace.model.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/5/3
 * @Description
 * @return $returns
 */
public enum AfterSaleAuditStatus {
    //审核状态:审核(初审)通过，审核(初审)不通过，收货审核通过，收货审核不通过
    PASS(3,1,"审核通过，退款中"),
    PASS_GOODS_FIRST(3,2,"初审审核通过，等待买家退货"),

    UNPASS(4,1,"审核未通过，退款关闭"),
    UNPASS_GOODS__FIRST(4,2,"初审核未通过，退货退款关闭"),

    PASS_GOODS_SECOND(6,2,"收货审核通过，退款中"),
    UNPASS_GOODS_SECOND(7,2,"收货审核未通过，退货退款关闭");

    private int status;
    private int bizType;//1 退款，2退款退货
    private String des;
    private AfterSaleAuditStatus(int status,int bizType,String des){
        this.status = status;
        this.bizType = bizType;
        this.des = des;
    }

    public static AfterSaleAuditStatus getAfterSaleAuditStatus(int status,int bizType){
        for (AfterSaleAuditStatus afterSaleAuditStatus : values()) {
            if (afterSaleAuditStatus.isEquals(status,bizType)) {
                return afterSaleAuditStatus;
            }
        }
        return null;
    }

    public static String getDesc(int status,int bizType) {
        for (AfterSaleAuditStatus afterSaleAuditStatus : values()) {
            if (afterSaleAuditStatus.isEquals(status,bizType)) {
                return afterSaleAuditStatus.getDes();
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public static Set<Integer> getAllStatus() {
        Set<Integer> allStatus = null;
        AfterSaleAuditStatus[] arr = AfterSaleAuditStatus.values();
        if(null != arr && arr.length > 0){
            allStatus = new  HashSet<Integer>();
            int a = 0;
            for (AfterSaleAuditStatus as: arr) {
                allStatus.add(as.getStatus());
            }
        }
        return allStatus;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isEquals(int status,int bizType) {
       boolean flag = this.status == status && this.bizType == bizType;
        return flag;
        // return this.status == status && this.status == bizType;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

}
