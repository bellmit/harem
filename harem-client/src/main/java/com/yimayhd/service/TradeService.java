package com.yimayhd.service;

import com.yimayhd.model.Trade;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface TradeService {
    /**
     * 获取交易列表(可带查询条件)
     * @return 交易列表
     */
    List<Trade> getList(Trade trade)throws Exception;
}
