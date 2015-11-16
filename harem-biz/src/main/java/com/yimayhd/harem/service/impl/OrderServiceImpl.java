package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Commodity;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author yebin
 *
 */
public class OrderServiceImpl implements OrderService {
	@Override
	public List<Order> getOrderList(Order order) throws Exception {
		List<Order> orderList = new ArrayList<Order>();
		int j = 10;
		// 是否有查询条件
		for (int i = 0; i < j; i++) {
			Order orders = new Order();
			orders.setId((long) i);
			orders.setOrderNO("20151109" + i);
			List<Commodity> commoditys = new ArrayList<Commodity>();
			int b = 3;
			for (int k = 0; k < b; k++) {
				Commodity commodity = new Commodity();
				commodity.setName("商品名称");
				commodity.setOrderId(Long.valueOf(10086));
				commodity.setNumber(Long.valueOf(2));
				commodity.setPrice(BigDecimal.valueOf(60));
				commoditys.add(commodity);
			}
			orders.setCommodityList(commoditys);
			orderList.add(orders);
		}
		return orderList;
	}

	@Override
	public Order getOrderById(long id) throws Exception {
		Order order = new Order();
		order.setOrderId(10086);
		order.setOrderNO("2015102710001");
		List<Commodity> commodityList = new ArrayList<Commodity>();
		for (int i = 0; i < 10; i++) {
			Commodity commodity = new Commodity();
			commodity.setOrderId(Long.valueOf(10086));
			commodity.setName("圆明园门票");
			commodity.setPrice(BigDecimal.valueOf(15));
			commodity.setNumber(Long.valueOf(2));
			commodityList.add(commodity);
		}

		Commodity commodity2 = new Commodity();
		commodity2.setOrderId(Long.valueOf(10086));
		commodity2.setName("故宫门票");
		commodity2.setPrice(BigDecimal.valueOf(60));
		commodity2.setNumber(Long.valueOf(2));
		commodityList.add(commodity2);
		order.setCommodityList(commodityList);
		return order;
	}

}
