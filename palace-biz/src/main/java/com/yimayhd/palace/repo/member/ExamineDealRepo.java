package com.yimayhd.palace.repo.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.dto.ExamineDealDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResultSupport;

public class ExamineDealRepo {
	private static final Logger logger = LoggerFactory.getLogger("ExamineDealRepo");
	@Autowired
	private ExamineDealService examineDealService;
	
	public BizPageResult<ExamineInfoDTO> queryExamineInfoDTOs(ExaminePageQueryDTO examinePageQueryDTO){
		BizPageResult<ExamineInfoDTO> result = new BizPageResult<ExamineInfoDTO>();
		if( examinePageQueryDTO == null ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result;
		}
		MemPageResult<ExamineInfoDTO> queryResult = examineDealService.queryMerchantExamineByPage(examinePageQueryDTO);
		if( queryResult == null || !queryResult.isSuccess() ){
			logger.error("queryMerchantExamineByPage   examinePageQueryDTO={},  Result={}", JSON.toJSONString(examinePageQueryDTO), JSON.toJSONString(queryResult) );
			if( queryResult == null ){
				result.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
			}
			return result;
		}
		result.setList(queryResult.getList());
		result.setTotalCount(queryResult.getTotalCount());
		return result ;
	}
	
	public BizResultSupport approve(ExamineDealDTO examineDealDTO){
		BizResultSupport result = new BizResultSupport() ;
		if( examineDealDTO == null || examineDealDTO.getReviewerId() <=0 || ExamineType.getByType(examineDealDTO.getType()) == null ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result;
		}
		MemResult<Boolean> approveResult = examineDealService.dealExamineInfo(examineDealDTO);
		if( approveResult == null || !approveResult.isSuccess() || approveResult.getValue() == null || !approveResult.getValue()){
			logger.error("dealExamineInfo   examineDealDTO={},  Result={}", JSON.toJSONString(examineDealDTO), JSON.toJSONString(approveResult) );
			if( approveResult == null ){
				result.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
			}else{
				//FIXME
				int code = approveResult.getErrorCode();
				if( MemberReturnCode.DB_EXAMINE_NOT_ING.getCode() == code ){
					result.setPalaceReturnCode(PalaceReturnCode.APPLY_APPROVE_STATUS_ERROR);
				}else{
					result.setPalaceReturnCode(PalaceReturnCode.APPROVE_FAILED);
				}
			}
			return result;
		}
		return result ;
	}
	
	public ExamineInfoDTO queryExamineInfoById(long id){
		MemResult<ExamineInfoDTO> queryResult = examineDealService.queryMerchantExamineInfoById(id);
		if( queryResult == null || !queryResult.isSuccess() || queryResult.getValue() == null ){
			logger.error("queryMerchantExamineInfoById   id={},  Result={}", JSON.toJSONString(id), JSON.toJSONString(queryResult) );
			return null;
		}
		ExamineInfoDTO dto = queryResult.getValue()	;
		return dto ;
	}
}
