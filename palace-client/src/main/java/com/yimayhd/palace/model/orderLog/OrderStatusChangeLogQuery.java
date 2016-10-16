package com.yimayhd.palace.model.orderLog;



import com.yimayhd.palace.base.PageQuery;



/**
 * Created by wangdi on 16/10/10.
 */
public class OrderStatusChangeLogQuery extends PageQuery{

    private static final long serialVersionUID = -8196260883114296399L;
    private String operationId;

    private String  bizNo;



    private String gmtCreatedStartStr;

    private String gmtCreatedEndStr;



    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
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
