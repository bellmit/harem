package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.trade.MainOrder;
import com.yimayhd.harem.model.trade.OrderDetails;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;

/**
 * 订单服务接口
 * 
 * @author yebin
 *
 */
public interface OrderService {
	/**
	 * 通过ID获取订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OrderDetails getOrderById(long id) throws Exception;

	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception;


	boolean buyerConfirmGoods(long id);

	boolean sellerSendGoods(long id);

	boolean refundOrder(long id);

}
