package com.yimayhd.palace.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.dto.ExamineDealDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.apply.ApplyConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.helper.apply.ApplyHelper;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.ApplyVO;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.palace.repo.member.ExamineDealRepo;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.UserDO;

public class ApplyBiz {
	@Autowired
	private ExamineDealRepo examineDealRepo ;
	@Autowired
	private UserRepo userRepo;
	
	public BizPageResult<ApplyVO> queryApplys(ApplyQuery applyQuery){
		ExaminePageQueryDTO examinePageQueryDTO = ApplyHelper.getExaminePageQueryDTO(applyQuery);
		BizPageResult<ApplyVO> result = new BizPageResult<ApplyVO>();
		if( examinePageQueryDTO == null ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result ;
		}
		examinePageQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
		BizPageResult<ExamineInfoDTO> queryResult = examineDealRepo.queryExamineInfoDTOs(examinePageQueryDTO);
		if( queryResult == null || !queryResult.isSuccess() ){
			if( queryResult == null ){
				result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
			}else{
				result.setPalaceReturnCode(queryResult.getPalaceReturnCode());
			}
			return result ;
		}
		List<ExamineInfoDTO> infos = queryResult.getList();
		List<ApplyVO> vos = ApplyConverter.dto2ApplyVOs(infos);
		List<Long> userIds = ApplyHelper.getSellerIds(vos);
		if( !CollectionUtils.isEmpty(userIds) ){
			List<UserDO> userDOs = userRepo.getUsers(userIds);
			ApplyHelper.fillUserInfos(vos, userDOs);
		}
		result.setList(vos);
		result.setTotalCount(queryResult.getTotalCount());
		return result;
	}
	
	public BizResultSupport approve(ApproveVO approveVO){
		ExamineDealDTO examineDealDTO = ApplyHelper.getExamineDealDTO(approveVO);
		BizResultSupport result = examineDealRepo.approve(examineDealDTO);
		return result ;
	}
}
