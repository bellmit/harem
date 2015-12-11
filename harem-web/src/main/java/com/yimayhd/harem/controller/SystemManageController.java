package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.vo.OrderVO;
import com.yimayhd.harem.service.OrderService;

@Controller
@RequestMapping("/systemManage")
public class SystemManageController extends BaseController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	public String membershipCardOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		PageVO<Order> pageVo = new PageVO<Order>(orderListQuery.getPageNumber(), orderListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/systemManage/roleList";
	}
	
	@RequestMapping(value = "/roleList2", method = RequestMethod.GET)
	public String membershipCardOrderList2(Model model, OrderListQuery orderListQuery) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		PageVO<Order> pageVo = new PageVO<Order>(orderListQuery.getPageNumber(), orderListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/systemManage/roleList";
	}
	
}
