package com.yimayhd.palace.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.dto.ExamineDealDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.ExamineResultDTO;
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
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.VerifyIdentityResult;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;

public class ApplyBiz {
	private static Logger log = LoggerFactory.getLogger("ApplyBiz");
	@Autowired
	private ExamineDealRepo examineDealRepo ;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private SessionManager sessionManager;
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
		result.setPageSize(examinePageQueryDTO.getPageSize());
		result.setPageNo(examinePageQueryDTO.getPageNo());
		return result;
	}
	
	public BizResultSupport approve(ApproveVO approveVO, long approverId){
		BizResultSupport result = new BizResultSupport() ;
		if( approveVO == null || approveVO.getId() <=0 ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result;
		}
		ExamineInfoDTO dto = examineDealRepo.queryExamineInfoById(approveVO.getId());
		if( dto == null ){
			result.setPalaceReturnCode(PalaceReturnCode.APPLY_RECORD_NOT_EXIT);
			return result ;
		}
//		VerifyIdentityResult verifyIdentityResult = examineDealRepo.verifyEleBankAccount(dto);
//		if (verifyIdentityResult == null || !verifyIdentityResult.isSuccess()) {
//			log.error("examineDealRepo.verifyEleBankAccount param:ExamineInfoDTO={},result:{}",JSON.toJSONString(dto),JSON.toJSONString(verifyIdentityResult));
//			result.setPalaceReturnCode(PalaceReturnCode.VERIFY_BANK_INFO_ERROR);
//			return result;
//		}
		ExamineDealDTO examineDealDTO = ApplyHelper.getExamineDealDTO(approveVO, approverId);
		examineDealDTO.setDomainId(dto.getDomainId());
		examineDealDTO.setSellerId(dto.getSellerId());
		examineDealDTO.setType(dto.getType());
		result = examineDealRepo.approve(examineDealDTO);
		return result ;
	}
	
	public BizResultSupport checkCorBankAccount(ExamineInfoDTO dto) {
		BizResultSupport result = new BizResultSupport() ;
		if (dto == null ) {
			log.error("param:ExamineInfoDTO={}",JSON.toJSONString(dto));
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result;
		}
		try {
			log.info("examineDealRepo.verifyCorBankAccount param:ExamineInfoDTO={}",JSON.toJSONString(dto));
			ResultSupport resultSupport = examineDealRepo.verifyCorBankAccount(dto);
			log.info("examineDealRepo.verifyCorBankAccount result:{}",JSON.toJSONString(resultSupport));
			if (resultSupport == null || !resultSupport.isSuccess()) {
				log.error("examineDealRepo.verifyCorBankAccount result:{}",JSON.toJSONString(resultSupport));
				result.setPalaceReturnCode(PalaceReturnCode.VERIFY_BANK_INFO_ERROR);
				return result;
			}
		} catch (Exception e) {
			log.error("param:ExamineInfoDTO={},error:{}",JSON.toJSONString(dto),e);
			result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
		}
		
		return result;
	}
	
	public ExamineResultDTO getCheckResult() {
		ExamineResultDTO checkResult = examineDealRepo.getCheckResult(sessionManager.getUserId());
		if (checkResult == null) {
			return null;
		}
		return checkResult;
		
	}
}
