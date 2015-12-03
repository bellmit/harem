package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.model.BizOrderVO;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface TradeService {
    /**
     * 获取交易列表(可带查询条件)
     * @return 交易列表
     */
    PageVO<BizOrderVO> getOrderList(TradeListQuery tradeListQuery)throws Exception;
    /**
     * 导出交易列表
     * @param tradeListQuery
     * @return
     */
    void exportOrderList(HttpServletResponse response,TradeListQuery tradeListQuery)throws Exception;

    /**
     * 支付列表
     * @param payListQuery
     * @return
     */
    PageVO<PayOrderDO> getPayOrderList(PayListQuery payListQuery)throws Exception;

    /**
     * 导出支付列表
     * @param payListQuery
     * @return
     */
    void exportPayOrderList(HttpServletResponse response,PayListQuery payListQuery)throws Exception;

}
