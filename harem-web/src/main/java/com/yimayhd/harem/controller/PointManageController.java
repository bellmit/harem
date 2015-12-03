package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.IMallPointRuleVO;
import com.yimayhd.harem.model.SendPointRule;
import com.yimayhd.harem.model.UsePointRule;
import com.yimayhd.harem.service.PointRuleService;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * 积分规则
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/PointManage")
public class PointManageController extends BaseController {

	@Autowired
	private PointRuleService pointRuleService;


	/**
	 * 积分发送规则
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/list", method = RequestMethod.GET)
	public String sendPointRule(Model model,BaseQuery baseQuery) throws Exception {
		IMallPointRuleResult iMallPointRuleResult = pointRuleService.getSendPointRuleNow();
		PageVO<IMallPointRuleResult> pageVO = pointRuleService.getSendPointRuleHistory(baseQuery);
		model.addAttribute("sendPointRule", iMallPointRuleResult);
		model.addAttribute("sendPointRuleList", pageVO.getItemList());
		return "/system/tradeUser/sendPointRule";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/toAdd", method = RequestMethod.GET)
	public String sendPointRuleToAdd() throws Exception {
		return "/system/tradeUser/sendPointRuleAdd";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/add", method = RequestMethod.POST)
	public String sendPointRuleAdd(IMallPointRuleVO iMallPointRuleVO) throws Exception {
		boolean success = pointRuleService.add(iMallPointRuleVO);
		if(success){
			return "/success";
		}
		return "/error";
	}
}
