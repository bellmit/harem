package com.yimayhd.harem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;

/**
 * 活动商品
 * 
 * @author xuzj
 */
@Controller
@RequestMapping("/B2C/activityProductManage")
public class ActivityProductManageController extends BaseController {
	


	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		
		return "/system/comm/activityProduct/edit";
	}

	
	
}
