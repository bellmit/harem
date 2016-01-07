package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/orderManage")
public class OrderManageController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderManageController.class);
	@Autowired
	private OrderService orderService;


	/**
	 * 退款
	 */
	@RequestMapping(value = "/refundOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo refundOrderById(long orderId) throws Exception {
		boolean flag = orderService.refundOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/buyerConfirmGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo buyerConfirmGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.buyerConfirmGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 发货+确认
	 */
	@RequestMapping(value = "/sellerSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo sellerSendGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.sellerSendGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}


	/**
	 * 关闭、取消订单
	 */
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo closeOrder(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.closeOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}



	/**
	 * 根据ID获取路线订单详情
	 * @return 路线订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrder/{id}", method = RequestMethod.GET)
	public String getRouteOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/routeOrderInfo";
	}

	/**
	 * 路线订单列表
	 * @return 路线订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrderList", method = RequestMethod.GET)
	public String routeOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.LINE.getBizType(),OrderBizType.FLIGHT_HOTEL.getBizType(),OrderBizType.SPOTS_HOTEL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
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
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/scenicSpotOrderInfo";
	}

	/**
	 * 景区订单列表
	 * 
	 * @return 景区订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/scenicSpotOrderList", method = RequestMethod.GET)
	public String scenicSpotOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.SPOTS.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
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
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/goodsOrderInfo";
	}

	/**
	 * 实物商品订单列表
	 * 
	 * @return 实物商品订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrderList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.NORMAL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
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
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/activityOrderInfo";
	}

	/**
	 * 活动订单
	 * 
	 * @return 活动订单
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityOrderList", method = RequestMethod.GET)
	public String activityOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.ACTIVITY.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
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
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/membershipCardOrderInfo";
	}

	/**
	 * 会员卡订单列表
	 * 
	 * @return 会员卡订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/membershipCardOrderList", method = RequestMethod.GET)
	public String membershipCardOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.MEMBER_RECHARGE.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/membershipCardOrderList";
	}

}