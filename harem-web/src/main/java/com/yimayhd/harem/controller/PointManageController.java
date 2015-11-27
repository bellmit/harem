package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.model.SendPointRule;
import com.yimayhd.harem.model.UsePointRule;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.HaMenuService;
import com.yimayhd.harem.service.SendPointRuleService;
import com.yimayhd.harem.service.UsePointRuleService;
import com.yimayhd.harem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 用户信息
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/PointManage")
public class PointManageController extends BaseController {

	@Autowired
	private SendPointRuleService sendPointRuleService;

	@Autowired
	private UsePointRuleService usePointRuleService;

	/**
	 * 积分发送规则
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/sendPointRule/list", method = RequestMethod.GET)
	public String sendPointRule(Model model) throws Exception {
		SendPointRule sendPointRule = sendPointRuleService.getSendPointRuleNow();
		UsePointRule usePointRule = usePointRuleService.getUsePointRuleNow();
		List<SendPointRule> sendPointRuleList = sendPointRuleService.getSendPointRuleHistory();
		model.addAttribute("sendPointRule", sendPointRule);
		model.addAttribute("usePointRule", usePointRule);
		model.addAttribute("sendPointRuleList", sendPointRuleList);
		return "/system/user/sendPointRule";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/sendPointRule/toAdd", method = RequestMethod.GET)
	public String sendPointRuleToAdd() throws Exception {
		return "/system/user/sendPointRuleAdd";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/sendPointRule/add", method = RequestMethod.POST)
	public String sendPointRuleAdd(SendPointRule sendPointRule) throws Exception {
		sendPointRuleService.add(sendPointRule);
		return "/success";
	}
}
