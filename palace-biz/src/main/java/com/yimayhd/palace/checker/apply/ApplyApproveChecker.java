package com.yimayhd.palace.checker.apply;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.palace.result.BizResultSupport;

public class ApplyApproveChecker {

	public static BizResultSupport checkApproveVO(ApproveVO approveVO){
		BizResultSupport result = new BizResultSupport() ;
		if( approveVO == null || approveVO.getId() <=0 ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
		}else if( !approveVO.isPass() && StringUtils.isBlank(approveVO.getReason()) ){
			result.setPalaceReturnCode(PalaceReturnCode.APPROVE_REJECT_REASON_EMPTY);
		}
		return result ;
	}
}
