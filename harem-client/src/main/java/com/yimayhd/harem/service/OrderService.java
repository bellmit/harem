package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.Order;

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
	List<Order> getOrderList(Order order) throws Exception;
}
