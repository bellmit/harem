package com.yimayhd.palace.model.orderLog;



import java.io.Serializable;
import java.util.Date;


/**
 * Created by wangdi on 16/10/10.
 */
public class OrderStatusChangeLogQuery implements Serializable {

    private static final long serialVersionUID = -8196260883114296399L;
    private static final int DEFAULT__SIZE = 10;
    private static final int DEFAULT__PAGE = 1;
    private long operationId;

    private String  bizNo;



    private String gmtCreatedStartStr;

    private String gmtCreatedEndStr;

    /**
     * 分页时的第几页
     */
    private Integer currentPage = DEFAULT__PAGE;
    /**
     * 每页数目，默认为 DEFAULT__SIZE
     */
    private Integer pageSize = DEFAULT__SIZE;


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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
