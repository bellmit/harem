package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

import java.util.List;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherListQuery extends BaseQuery{

    private String title;//标题
    private int entityType;
    private int voucherType;//券类型
    private int status;
    private String issueType;//发卷方式
    private String beginDate;
    private String endDate;
    private String userName;
    private String vid;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    private List<Integer> ids;
    private long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }


    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
