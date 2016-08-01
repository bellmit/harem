package com.yimayhd.palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.SettlementVO;
import com.yimayhd.palace.model.query.SettlementQuery;
import com.yimayhd.palace.service.SettlementService;
import com.yimayhd.pay.client.model.enums.SettlementStatus;
import com.yimayhd.pay.client.model.enums.eleaccount.AccountType;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;

/** 
* @ClassName: VerifyManageController 
* @Description: 九休结算管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/settlement")
public class SettlementManageController extends BaseController {
	
	@Autowired
	private SettlementService settlementService;
	
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 结算列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, SettlementQuery query) throws Exception {
		
		PageVO<SettlementVO> pageVo = settlementService.querySettlements(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		model.addAttribute("accountTypePerson", AccountType.PERSON.getType());
		model.addAttribute("settlementFail", SettlementStatus.SETTLEMENT_FAIL.getValue());
		
		return "/system/settlement/list";
	}
	
	/**
	 * 结算明细
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, SettlementQuery query) throws Exception {
		
		PageVO<SettlementVO> pageVo = settlementService.querySettlementDetails(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		
		return "/system/settlement/detail";
	}
	
	/**
	 * 结算失败重新提交
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/settlementFailRetry", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo settlementFailRetry(Model model, SettlementVO settlementVO) throws Exception {
		try {
			UserDO user = sessionManager.getUser();
			settlementVO.setOperatorId(user.getId());
			settlementVO.setOperatorName(user.getName());
			boolean ret = settlementService.settlementFailRetry(settlementVO);
			return  ResponseVo.success(ret);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
	/**
	 * 操作记录
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/operateLog", method = RequestMethod.GET)
	public String operateLog(Model model) throws Exception {
		return "/system/settlement/opereateLog";
	}
	
}
