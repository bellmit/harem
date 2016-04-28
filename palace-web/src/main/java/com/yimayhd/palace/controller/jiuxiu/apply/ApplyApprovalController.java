package com.yimayhd.palace.controller.jiuxiu.apply;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.biz.ApplyBiz;
import com.yimayhd.palace.checker.apply.ApplyApproveChecker;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.ApplyVO;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.session.manager.SessionManager;

@Controller
@RequestMapping("/jiuxiu/apply")
public class ApplyApprovalController extends BaseController{
	@Autowired
	private SessionManager sessionManager ;
	@Autowired
	private ApplyBiz applyBiz ;
	@Autowired
	private ExamineDealService examineDealServiceRef;
	
	
	@RequestMapping(value="/list")
	public String applyApproval(Model model){
		ExamineType[] types =ExamineType.values();
		ExamineStatus[] statuses = ExamineStatus.values() ;
		model.addAttribute("types", types);
		model.addAttribute("statuses", statuses);
		return "/system/apply/list";
	}
	
	@RequestMapping(value="/queryApplies")
	public String getApplies(ApplyQuery applyQuery){
		BizPageResult<ApplyVO> result = applyBiz.queryApplys(applyQuery);
		return null;
	}
	
	@RequestMapping(value="/query")
	@ResponseBody
	public BizPageResult<ApplyVO> queryApplies(ApplyQuery applyQuery){
		BizPageResult<ApplyVO> result = applyBiz.queryApplys(applyQuery);
		return result;
	}
	
	@RequestMapping(value="/approve")
	@ResponseBody
	public BizResultSupport approve(ApproveVO approveVO){
		BizResultSupport checkResult = ApplyApproveChecker.checkApproveVO(approveVO);
		if( !checkResult.isSuccess() ){
			return checkResult ;
		}
		long userId = sessionManager.getUserId();
		BizResultSupport result = applyBiz.approve(approveVO, userId);
		return result;
	}
	
	@RequestMapping(value="detail")
	public String getMerchantDetail(Model model,long id){
		try {
			MemResult<ExamineInfoDTO> result = examineDealServiceRef.queryMerchantExamineInfoById(id);
			if(result.isSuccess() && null !=result.getValue()){
				model.addAttribute("examineInfo", result.getValue());
				model.addAttribute("type", result.getValue().getType());
				model.addAttribute("status", result.getValue().getExaminStatus());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "/system/apply/detail";
	}
}
