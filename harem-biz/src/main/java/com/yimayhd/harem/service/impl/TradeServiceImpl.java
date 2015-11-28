package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.service.TradeService;
import com.yimayhd.harem.util.excel.JxlFor2003;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.query.PayOrderQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.service.QueryPayOrderService;
import org.apache.http.message.BasicNameValuePair;
import org.apache.mina.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TradeServiceImpl implements TradeService {
    @Autowired
    private QueryPayOrderService QueryPayOrderServiceRef;
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

    @Override
    public PageVO<PayOrderDO> getPayOrderList(PayListQuery payListQuery)throws Exception{
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        //payOrderQuery.setTradeNo(payListQuery.getTradeNo());
        //SessionUtil
        payOrderQuery.setSellerId(1);
        //TODO 开始结束时间暂时不支持，未set
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        PageVO<PayOrderDO> pageVO = new PageVO<PayOrderDO>(payListQuery.getPageSize(),payPageResultDTO.getTotalCount(),payPageResultDTO.getPageNo(),payPageResultDTO.getList());
        return pageVO;
    }

    @Override
    public void exportPayOrderList(HttpServletResponse response,PayListQuery payListQuery)throws Exception {
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setTradeNo(payListQuery.getTradeNo());
        //TODO 开始结束时间暂时不支持(导出要限制时间)，未set
        payOrderQuery.setSellerId(1);
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
        headList.add(new BasicNameValuePair("tradeNo", "交易号"));
        headList.add(new BasicNameValuePair("id", "商户订单号"));
        headList.add(new BasicNameValuePair("subject", "商品信息"));
        headList.add(new BasicNameValuePair("buyerAccount", "对方账号"));
        headList.add(new BasicNameValuePair("totalAmount", "交易金额"));
        headList.add(new BasicNameValuePair("payStatus", "状态"));
        JxlFor2003.exportExcel(response, "payList.xls", payPageResultDTO.getList(), headList);

    }
}
