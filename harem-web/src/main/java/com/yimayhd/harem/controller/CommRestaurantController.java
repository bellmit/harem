package com.yimayhd.harem.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;

/**
 * 商品管理
 * 
 * @autho yebin
 *
 */
@Controller
@RequestMapping("/B2C/comm/restaurant")
public class CommRestaurantController extends BaseController {
	/**
	 * 餐厅
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long id) throws Exception {
		put("query", new ArrayList<Object>());
		return "/system/goods/route/info";
	}
}
