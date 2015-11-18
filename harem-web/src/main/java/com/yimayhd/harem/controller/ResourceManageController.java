package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.query.base.Page;
import com.yimayhd.harem.model.query.base.PageRequest;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantService;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceManage")
public class ResourceManageController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping(value = "/restaurant/list", method = RequestMethod.GET)
	public String restaurantList(RestaurantListQuery restaurantListQuery) throws Exception {
		PageRequest<RestaurantListQuery> pageRequest = newPageRequest(request, restaurantListQuery);
		Page<Restaurant> page = restaurantService.getList(pageRequest);
		put("page", page);
		put("query", restaurantListQuery);
		return "/system/resource/restaurant/list";
	}

	@RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
	public String restaurant(@PathVariable("id") Long id) throws Exception {
		Restaurant restaurant = restaurantService.getById(id);
		put("restaurant", restaurant);
		return "/system/resource/restaurant/Info";
	}
}
