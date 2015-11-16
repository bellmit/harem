package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Commodity;
import com.yimayhd.harem.model.Contact;
import com.yimayhd.harem.model.Coupon;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.Tourist;
import com.yimayhd.harem.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
				commodity.setImageUrl("图片");
				commodity.setDescription("圆明园门票");
				commodity.setPrice(BigDecimal.valueOf(7199));
				commodity.setAmount(12);
				commodity.setTradeState(1);
				commodity.setDiscount("-");
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
		// 订单状态
		order.setOrderState(1);
		// 买家信息
		order.setBuyerMobile("13529000000");
		order.setBuyerNickname("Tracy");
		order.setBuyerName("张三");
		// 订单基础信息
		order.setOrderNO("2015102710001");
		order.setOrderTime(new Date());
		order.setPaymentTime(new Date());
		order.setPaymentMethod("微信/支付宝/银联");
		order.setBuyerNote("张三的备注");
		order.setInvoice("个人 / 公司抬头");
		order.setCustomerServiceNote("客服的备注");
		// 联系人
		List<Contact> contacts = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setName("张三");
		contact.setMobile("13529000000");
		contact.setEmail("tracy648@163.com");
		contacts.add(contact);
		order.setContacts(contacts);
		// 游客
		List<Tourist> tourists = new ArrayList<Tourist>();
		Tourist tourist1 = new Tourist();
		tourist1.setTouristType(1);
		tourist1.setName("张三");
		tourist1.setMobile("13529000000");
		tourist1.setDocType(1);
		tourist1.setDocNo("530112199106150000");
		tourists.add(tourist1);
		Tourist tourist2 = new Tourist();
		tourist2.setTouristType(2);
		tourist2.setName("张四");
		tourist2.setMobile("13529000000");
		tourist2.setDocType(1);
		tourist2.setDocNo("530112199106150000");
		tourists.add(tourist2);
		order.setTourists(tourists);
		// 订单商品信息
		order.setOrderTotalPrice(BigDecimal.valueOf(12345));
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon = new Coupon();
		coupon.setCount(1);
		coupon.setPrice(BigDecimal.valueOf(20));
		coupons.add(coupon);
		order.setCoupons(coupons);
		// 商品列表
		List<Commodity> commodityList = new ArrayList<Commodity>();
		for (int i = 0; i < 10; i++) {
			Commodity commodity = new Commodity();
			commodity.setImageUrl("图片");
			commodity.setDescription("圆明园门票");
			commodity.setPrice(BigDecimal.valueOf(7199));
			commodity.setAmount(12);
			commodity.setTradeState(1);
			commodity.setDiscount("-");
			commodityList.add(commodity);
		}
		order.setCommodityList(commodityList);
		return order;
	}

}
