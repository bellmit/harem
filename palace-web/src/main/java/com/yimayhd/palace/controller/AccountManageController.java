package com.yimayhd.palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.EleAccBalanceResultVO;
import com.yimayhd.palace.model.EleAccountBillVO;
import com.yimayhd.palace.model.query.AccountQuery;
import com.yimayhd.palace.service.AccountService;

/** 
* @ClassName: AccountManageController 
* @Description: 九休账户管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/account")
public class AccountManageController extends BaseController {
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 账户列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, AccountQuery query) throws Exception {
		
		EleAccBalanceResultVO result = accountService.queryEleAccBalance(query);
		model.addAttribute("pageVo", result.getEleAccBalanceVOPage());
		model.addAttribute("totalAmount", result.getTotalAmount());
		model.addAttribute("query", query);
		
		return "/system/account/list";
	}
	
	/**
	 * 账户明细
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, AccountQuery query) throws Exception {
		
		PageVO<EleAccountBillVO> pageVo = accountService.queryEleAccBillDetail(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		
		return "/system/account/detail";
	}
	
}
