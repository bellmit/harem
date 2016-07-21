package com.yimayhd.palace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.PayAuditOrderVO;
import com.yimayhd.palace.model.PayAuditResultVO;
import com.yimayhd.palace.model.query.AuditQuery;
import com.yimayhd.palace.service.AuditService;

/** 
* @ClassName: VerifyManageController 
* @Description: 九休对账管理
* @author create by hongfei.guo @ 2016年7月15日 下午16:48 
*/

@Controller
@RequestMapping("/jiuxiu/audit")
public class AuditManageController extends BaseController {
	
	@Autowired
	private AuditService auditService;
	
	/**
	 * 渠道对账进度查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public String schedule(Model model, AuditQuery query) throws Exception {
		
		PageVO<PayAuditResultVO> pageVo = auditService.queryAuditProgress(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		
		return "/system/audit/schedule";
	}
	
	/**
	 * 渠道对账汇总查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public String summary(Model model, AuditQuery query) throws Exception {
		
		List<PayAuditResultVO> pageVo = auditService.queryAuditResult(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		
		return "/system/audit/summary";
	}
	
	/**
	 * 渠道对账明细查询
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, AuditQuery query) throws Exception {
		
		PageVO<PayAuditOrderVO> pageVo = auditService.queryAuditOrder(query);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("query", query);
		
		return "/system/audit/detail";
	}
}
