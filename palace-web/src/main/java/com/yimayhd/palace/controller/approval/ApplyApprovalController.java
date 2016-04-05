package com.yimayhd.palace.controller.approval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yimayhd.palace.model.apply.ApplyQueryVO;

@Controller
@RequestMapping("apply")
public class ApplyApprovalController {
	
	@RequestMapping(value="/approval")
	public String applyApproval(ApplyQueryVO queryVO){
		
		return "/system/apply/list";
	}
}
