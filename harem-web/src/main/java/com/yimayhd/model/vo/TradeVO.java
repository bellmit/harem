package com.yimayhd.model.vo;

import com.yimayhd.model.Trade;
import com.yimayhd.model.query.TradeListQuery;

/**
 * Created by Administrator on 2015/10/29.
 */
public class TradeVO extends Trade {
    private TradeListQuery tradeListQuery;//查询条件

    public TradeListQuery getTradeListQuery() {
        return tradeListQuery;
    }

    public void setTradeListQuery(TradeListQuery tradeListQuery) {
        this.tradeListQuery = tradeListQuery;
    }
}
