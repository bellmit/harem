package com.yimayhd.harem.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.Goods;

/**
 * 商品管理
 * 
 * @autho yebin
 *
 */
@Controller
@RequestMapping("/B2C/goodsManage")
public class GoodsManageController extends BaseController {

	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public String restaurantList(Long id) throws Exception {
		put("query", new ArrayList<Object>());
		return "/system/goods/route/info";
	}
}
