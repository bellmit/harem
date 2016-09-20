package com.yimayhd.palace.repo.member;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.MemberReturnCode;
import com.yimayhd.membercenter.client.dto.ExamineDealDTO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.pay.client.model.enums.eleaccount.AccountType;
import com.yimayhd.pay.client.model.enums.verify.VerifyIdentityType;
import com.yimayhd.pay.client.model.param.eleaccount.verify.VerifyCmpEleAccountDTO;
import com.yimayhd.pay.client.model.param.eleaccount.verify.VerifyEleAccountDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.model.result.eleaccount.VerifyIdentityResult;
import com.yimayhd.pay.client.service.eleaccount.EleAccInfoService;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ExamineDealRepo {
	private static final Logger logger = LoggerFactory.getLogger("ExamineDealRepo");
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private EleAccInfoService eleAccInfoServiceRef;
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

		MemResult<Boolean> approveResult = examineDealService.refuseMerchantOrAuditTalent(examineDealDTO);
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
	/**
	 *
	* created by zhangxiaoyang
	* @date 2016年8月1日
	* @Title: verifyEleBankAccount
	* @Description: 验证非企业银行帐号
	* @param @param dto
	* @param @return    设定文件
	* @return VerifyIdentityResult    返回类型
	* @throws
	 */
	public VerifyIdentityResult verifyEleBankAccount(ExamineInfoDTO dto) {
		logger.info("param:ExamineInfoDTO={}",JSON.toJSONString(dto));
		try {

			VerifyEleAccountDTO verifyEleAccountDTO = new VerifyEleAccountDTO();
			verifyEleAccountDTO.setUserId(dto.getSellerId());
			verifyEleAccountDTO.setBankCardNo(dto.getAccountNum());
			verifyEleAccountDTO.setBankName(dto.getAccountBankName());
			verifyEleAccountDTO.setBankNo(dto.getSettlementCard());
			verifyEleAccountDTO.setIdNo(dto.getOpenerCard());
			verifyEleAccountDTO.setMerchantType(MerchantType.TALENT.getType());
			if (Integer.parseInt(dto.getAccountType()) == AccountType.PERSON.getType()) {

				verifyEleAccountDTO.setVerifyIdentityType(VerifyIdentityType.OPEN_ELE_ACCOUNT.getType());
			}else {

				verifyEleAccountDTO.setVerifyIdentityType(VerifyIdentityType.OPEN_CMP_ELE_ACCOUNT.getType());

			}
			verifyEleAccountDTO.setUserName(dto.getPrincipleName());
			if (StringUtils.isNotBlank(dto.getOpenerTel())) {

				verifyEleAccountDTO.setMobilePhone(dto.getOpenerTel());
			}else {
				verifyEleAccountDTO.setMobilePhone(dto.getPrincipleTel());

			}
			logger.info("eleAccInfoServiceRef.verifyEleAccount ,param:verifyEleAccountDTO={}",JSON.toJSONString(verifyEleAccountDTO));
			VerifyIdentityResult verifyResult = eleAccInfoServiceRef.verifyEleAccount(verifyEleAccountDTO);
			logger.info("eleAccInfoServiceRef.verifyEleAccount ,result:VerifyIdentityResult={}",JSON.toJSONString(verifyResult));

			return verifyResult;
		} catch (Exception e) {
			logger.info("eleAccInfoServiceRef.verifyEleAccount ,param:ExamineInfoDTO={},error:{}",JSON.toJSONString(dto),e);
		}
		return null;
	}
	/**
	 *
	* created by zhangxiaoyang
	* @date 2016年8月1日
	* @Title: verifyCorBankAccount
	* @Description: 验证企业银行帐号
	* @param @param dto
	* @param @return    设定文件
	* @return ResultSupport    返回类型
	* @throws
	 */
	public ResultSupport verifyCorBankAccount(ExamineInfoDTO dto) {
		logger.info("param:ExamineInfoDTO={}",JSON.toJSONString(dto));
		try {
			VerifyCmpEleAccountDTO verifyCmpEleAccountDTO = new VerifyCmpEleAccountDTO();
			verifyCmpEleAccountDTO.setUserId(dto.getSellerId());
			verifyCmpEleAccountDTO.setBankAccountType(Integer.parseInt(dto.getAccountType()));
			verifyCmpEleAccountDTO.setOpenBankNo(dto.getSettlementCard());
			verifyCmpEleAccountDTO.setOpenBankName(dto.getFinanceOpenBankName());
			verifyCmpEleAccountDTO.setOpenAcctNo(dto.getAccountNum());
			verifyCmpEleAccountDTO.setOpenAcctName(dto.getFinanceOpenName());
			if (StringUtils.isNotBlank(dto.getOpenerTel())) {

				verifyCmpEleAccountDTO.setContactMobile(dto.getOpenerTel());
			}else {
				verifyCmpEleAccountDTO.setContactMobile(dto.getPrincipleTel());

			}
			verifyCmpEleAccountDTO.setContactIdNo(dto.getOpenerCard());
			verifyCmpEleAccountDTO.setCorpName(dto.getSellerName());
			if (dto.getAccountType().equals(String.valueOf(AccountType.PERSON.getType()))) {

				verifyCmpEleAccountDTO.setContactName(dto.getFinanceOpenName());
			}
			if (dto.getAccountType().equals(String.valueOf(AccountType.COMPANY.getType()))) {

				verifyCmpEleAccountDTO.setContactName(dto.getPrincipleName());
			}
			verifyCmpEleAccountDTO.setCorpBusiCode(dto.getSaleLicenseNumber());
			logger.info("eleAccInfoServiceRef.verifyCmpEleAccount param:VerifyCmpEleAccountDTO={}",JSON.toJSONString(verifyCmpEleAccountDTO));
			ResultSupport verifyResult = eleAccInfoServiceRef.verifyCmpEleAccount(verifyCmpEleAccountDTO);
			logger.info("eleAccInfoServiceRef.verifyCmpEleAccount ,result:ResultSupport={}",JSON.toJSONString(verifyResult));

			return verifyResult;
		} catch (Exception e) {
			logger.info("eleAccInfoServiceRef.verifyEleAccount ,param:ExamineInfoDTO={},error:{}",JSON.toJSONString(dto),e);
		}
		return null;
	}
	
}
