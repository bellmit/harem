package com.yimayhd.palace.model.orderLog;


import com.yimayhd.palace.base.PageQuery;

import java.util.Date;


/**
 * Created by wangdi on 16/10/10.
 */
public class OrderStatusChangeLogQuery extends PageQuery {

    private static final long serialVersionUID = -8196260883114296399L;
    private long operationId;

    private String  bizNo;

    private Date gmtCreatedStart;

    private Date gmtCreatedEnd;


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
}
