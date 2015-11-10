package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TrendListQuery extends BaseQuery {
    private Long tag;
    private Integer trendStatus;
    private String Tel;
    private String name;

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

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
