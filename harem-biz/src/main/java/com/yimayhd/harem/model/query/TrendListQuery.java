package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TrendListQuery extends BaseQuery {
    private Long tag;
    private Integer trendStatus;
    private String userTel;
    private String userName;

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
}
