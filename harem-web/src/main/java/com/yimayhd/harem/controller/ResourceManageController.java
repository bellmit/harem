package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.common.Page;
import com.yimayhd.harem.common.PageRequest;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.service.RestaurantService;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/ResourceManage")
public class ResourceManageController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping(value = "/restaurant/list", method = RequestMethod.GET)
	public String restaurantList() throws Exception {
		PageRequest pageRequest = newPageRequest(request);
		Page<Restaurant> page = restaurantService.getList(pageRequest);
		put("page", page);
		return "/system/resourrestaurant/list";
	}
}
