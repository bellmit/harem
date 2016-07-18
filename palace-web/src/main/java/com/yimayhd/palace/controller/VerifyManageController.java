package com.yimayhd.palace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.palace.base.BaseController;

/** 
* @ClassName: VerifyManageController 
* @Description: 九休对账管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/verify")
public class VerifyManageController extends BaseController {
	
	/**
	 * 渠道对账进度查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public String schedule(Model model) throws Exception {
		return "/system/verify/schedule";
	}
	
	/**
	 * 渠道对账汇总查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public String summary(Model model) throws Exception {
		return "/system/verify/summary";
	}
	
	/**
	 * 渠道对账明细查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model) throws Exception {
		return "/system/verify/detail";
	}
}
