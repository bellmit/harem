package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.vo.OrderVO;
import com.yimayhd.harem.service.OrderService;

//import com.yimayhd.service.MessageCodeService;

/**
 * 订单管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/orderManage")
public class OrderManageController extends BaseController {
	@Autowired
	private OrderService orderService;

	/**
	 * 根据ID获取路线订单详情
	 * 
	 * @return 路线订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrder/{id}", method = RequestMethod.GET)
	public String getRouteOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/system/order/routeOrderInfo";
	}

	/**
	 * 路线订单列表
	 * 
	 * @return 路线订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrderList", method = RequestMethod.GET)
	public String routeOrderList(Model model, OrderListQuery orderListQuery, PageVo pageVo) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		pageVo.setTotalSum((long) 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/order/routeOrderList";
	}

	/**
	 * 根据ID获取景区订单详情
	 * 
	 * @return 景区订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/scenicSpotOrder/{id}", method = RequestMethod.GET)
	public String getScenicSpotOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/system/order/scenicSpotOrderInfo";
	}

	/**
	 * 景区订单列表
	 * 
	 * @return 景区订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/scenicSpotOrderList", method = RequestMethod.GET)
	public String scenicSpotOrderList(Model model, OrderListQuery orderListQuery, PageVo pageVo) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		pageVo.setTotalSum((long) 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/order/scenicSpotOrderList";
	}

	/**
	 * 根据ID获取实物商品订单详情
	 * 
	 * @return 实物商品订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrder/{id}", method = RequestMethod.GET)
	public String getGoodOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/system/order/goodsOrderInfo";
	}

	/**
	 * 实物商品订单列表
	 * 
	 * @return 实物商品订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrderList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, OrderListQuery orderListQuery, PageVo pageVo) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		pageVo.setTotalSum((long) 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/order/goodsOrderList";
	}

	/**
	 * 根据ID获取活动订单详情
	 * 
	 * @return 活动订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityOrder/{id}", method = RequestMethod.GET)
	public String getActivityOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/system/order/activityOrderInfo";
	}

	/**
	 * 活动订单
	 * 
	 * @return 活动订单
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityOrderList", method = RequestMethod.GET)
	public String activityOrderList(Model model, OrderListQuery orderListQuery, PageVo pageVo) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		pageVo.setTotalSum((long) 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/order/activityOrderList";
	}

	/**
	 * 根据ID获取会员卡订单详情
	 * 
	 * @return 活动会员卡详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/membershipCardOrder/{id}", method = RequestMethod.GET)
	public String getMembershipCardOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Order order = orderService.getOrderById(id);
		model.addAttribute("order", order);
		return "/system/order/membershipCardOrderInfo";
	}

	/**
	 * 会员卡订单列表
	 * 
	 * @return 会员卡订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/membershipCardOrderList", method = RequestMethod.GET)
	public String membershipCardOrderList(Model model, OrderListQuery orderListQuery, PageVo pageVo) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		pageVo.setTotalSum((long) 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/order/membershipCardOrderList";
	}

}
