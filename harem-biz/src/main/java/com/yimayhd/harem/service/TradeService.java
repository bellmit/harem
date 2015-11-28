package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 支付列表
     * @param payListQuery
     * @return
     */
    PageVO<PayOrderDO> getPayOrderList(PayListQuery payListQuery)throws Exception;

    /**
     * 支付列表
     * @param payListQuery
     * @return
     */
    void exportPayOrderList(HttpServletResponse response,PayListQuery payListQuery)throws Exception;

}
