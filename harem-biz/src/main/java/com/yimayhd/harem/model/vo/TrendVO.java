package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.model.Trend;
import com.yimayhd.harem.model.query.TrendListQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TrendVO {
    private Trend trend;
    private TrendListQuery trendListQuery;

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public TrendListQuery getTrendListQuery() {
        return trendListQuery;
    }

    public void setTrendListQuery(TrendListQuery trendListQuery) {
        this.trendListQuery = trendListQuery;
    }
}
