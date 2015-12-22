package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

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
	Order getOrderById(long id) throws Exception;

	/**
	 * 获取订单列表
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	PageVO<BizOrderDO> getOrderList(OrderListQuery orderListQuery) throws Exception;
}
