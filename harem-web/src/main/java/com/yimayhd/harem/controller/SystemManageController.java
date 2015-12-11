package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.model.vo.OrderVO;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.harem.service.SystemManageService;

@Controller
@RequestMapping("/systemManage")
public class SystemManageController extends BaseController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	SystemManageService systemManageService;

	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	public String roleList(Model model, OrderListQuery orderListQuery) throws Exception {
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderListQuery(orderListQuery);
		List<Order> activityOrderList = orderService.getOrderList(orderVO);
		PageVO<Order> pageVo = new PageVO<Order>(orderListQuery.getPageNumber(), orderListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderListQuery", orderListQuery);
		model.addAttribute("orderList", activityOrderList);
		return "/system/systemManage/roleList";
	}
	
	@RequestMapping(value = "/roleList3", method = RequestMethod.GET)
	public String roleList2(Model model, RoleListQuery roleListQuery) throws Exception {
		
		//RoleListQuery roleListQuery = new RoleListQuery();
		roleListQuery.setPageNumber(0);
		roleListQuery.setPageSize(5);

		System.out.println(JSON.toJSONString(systemManageService.getListNew(roleListQuery)));
		List<HaRoleDO> roleList = systemManageService.getListNew(roleListQuery);
		
		PageVO<Order> pageVo = new PageVO<Order>(roleListQuery.getPageNumber(), roleListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("roleListQuery", roleListQuery);
		model.addAttribute("roleList", roleList);
		return "/system/systemManage/roleList2";
	}
	
}
