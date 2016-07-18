package com.yimayhd.palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.tair.TcCacheManager;
import com.yimayhd.user.session.manager.SessionManager;

/** 
* @ClassName: AccountManageController 
* @Description: 九休账户管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/account")
public class AccountManageController extends BaseController {
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private TcCacheManager	tcCacheManager;
	
	/**
	 * 账户列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		return "/system/account/list";
	}
	
	/**
	 * 账户明细
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model) throws Exception {
		return "/system/account/detail";
	}
	
}
