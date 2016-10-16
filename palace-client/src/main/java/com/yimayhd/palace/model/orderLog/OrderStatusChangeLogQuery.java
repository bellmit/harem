package com.yimayhd.palace.model.orderLog;



import java.io.Serializable;
import java.util.Date;


/**
 * Created by wangdi on 16/10/10.
 */
public class OrderStatusChangeLogQuery implements Serializable {

    private static final long serialVersionUID = -8196260883114296399L;
    private long operationId;

    private String  bizNo;

    private Date gmtCreatedStart;

    private Date gmtCreatedEnd;

    private String gmtCreatedStartStr;

    private String gmtCreatedEndStr;


    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public Date getGmtCreatedStart() {
        return gmtCreatedStart;
    }

    public void setGmtCreatedStart(Date gmtCreatedStart) {
        this.gmtCreatedStart = gmtCreatedStart;
    }

    public Date getGmtCreatedEnd() {
        return gmtCreatedEnd;
    }

    public void setGmtCreatedEnd(Date gmtCreatedEnd) {
        this.gmtCreatedEnd = gmtCreatedEnd;
    }

    public String getGmtCreatedStartStr() {
        return gmtCreatedStartStr;
    }

    public void setGmtCreatedStartStr(String gmtCreatedStartStr) {
        this.gmtCreatedStartStr = gmtCreatedStartStr;
    }

    public String getGmtCreatedEndStr() {
        return gmtCreatedEndStr;
    }

    public void setGmtCreatedEndStr(String gmtCreatedEndStr) {
        this.gmtCreatedEndStr = gmtCreatedEndStr;
    }
}
