package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;

/**
 * Created by Administrator on 2015/10/29.
 */
public class OrderVO extends Order {
	private OrderListQuery orderListQuery;//查询条件

	public OrderListQuery getOrderListQuery() {
		return orderListQuery;
	}

	public void setOrderListQuery(OrderListQuery orderListQuery) {
		this.orderListQuery = orderListQuery;
	}
	
}
