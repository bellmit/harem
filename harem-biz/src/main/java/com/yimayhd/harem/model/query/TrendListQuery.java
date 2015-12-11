package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TrendListQuery extends BaseQuery {
    private Long tag;
    private Integer trendStatus;
    private String userTel;
    private String userName;
    private String beginDate;
    private String endDate;

    public Long getTag() {
        return tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }

    public Integer getTrendStatus() {
        return trendStatus;
    }

    public void setTrendStatus(Integer trendStatus) {
        this.trendStatus = trendStatus;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
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
}
