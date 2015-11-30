package com.yimayhd.harem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yimayhd.harem.base.BaseController;

/**
 * 首页管理
 * @author xusq
 *
 */
@Controller
@RequestMapping("/B2C/homeManage")
public class HomeManageController extends BaseController{

	@RequestMapping("/index")
	public String toHomePageManage(){
		return "/system/home/index";
	}
}
