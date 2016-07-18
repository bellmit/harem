package com.yimayhd.palace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.palace.base.BaseController;

/** 
* @ClassName: VerifyManageController 
* @Description: 九休结算管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/settle")
public class SettleManageController extends BaseController {
	
	/**
	 * 结算列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		return "/system/settle/list";
	}
	
	/**
	 * 结算明细
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model) throws Exception {
		return "/system/settle/detail";
	}
	
	/**
	 * 操作记录
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/operateLog", method = RequestMethod.GET)
	public String operateLog(Model model) throws Exception {
		return "/system/settle/opereateLog";
	}
	
}
