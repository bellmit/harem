package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.service.TradeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TradeServiceImpl implements TradeService {
    @Override
    public List<Trade> getList(Trade trade) throws Exception{
        List<Trade> tradeList = new ArrayList<Trade>();
        int j = 10;
        //是否有查询条件
        for (int i = 0;i <= j;i++){
            Trade tradeData = new Trade();
            tradeData.setId((long) i);
            tradeData.setTradNO("2008100109"+i);//交易编号
            tradeData.setUserName("张三" + i);
            tradeData.setPhone("18618162075");
            tradeData.setShouldMoney(BigDecimal.valueOf(588.88));
            tradeData.setTradeMoney(BigDecimal.valueOf(488.88));
            tradeData.setReduceMoney(BigDecimal.valueOf(100));
            tradeData.setUsePoint(BigDecimal.valueOf(200));
            tradeData.setSendPoint(BigDecimal.valueOf(500));
            tradeData.setPoint(BigDecimal.valueOf(1200));
            tradeData.setTradeStatus(1);
            tradeData.setTradeTime(new Date());
            tradeData.setTerminalName("海南三亚烧烤大排档");
            tradeData.setCashierName("晓红");
            tradeData.setRemark("大虾消费");
            tradeList.add(tradeData);
        }
        return tradeList;
    }
}
