package com.yimayhd.palace.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.back.TalentInfoDealService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.enums.RegisterType;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 
 * @author zhangxy
 *
 */
public class MerchantRepo {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MerchantService userMerchantServiceRef;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private TalentInfoDealService talentInfoDealService;
	public BizResultSupport addDeliciousFood(MerchantVO vo) {
		BizResultSupport resultSupport = new BizResultSupport();
		if (vo == null) {
			log.error("params is "+JSON.toJSONString(vo));
			resultSupport.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return resultSupport;
		}
		try {
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setRegisterType(RegisterType.NO_ACCOUNT);
			registerDTO.setDomainId(Constant.DOMAIN_JIUXIU);
			BaseResult<UserDO> registerWithoutAccount = userServiceRef.registerWithoutAccount(registerDTO);
			if (registerWithoutAccount == null || !registerWithoutAccount.isSuccess() || registerWithoutAccount.getValue() == null) {
				log.error("generate merchant's id of food error");
				throw new BaseException("生产注册美食商家id失败");
			}
			BaseResult<MerchantDO> saveMerchantResult = userMerchantServiceRef.saveMerchant(vo.getMerchantDO(vo,registerWithoutAccount.getValue().getId()));
			if (saveMerchantResult == null) {
				resultSupport.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
				return resultSupport;
			}
			if (!saveMerchantResult.isSuccess()) {
				log.error("result:{} "+JSON.toJSONString(saveMerchantResult));
				resultSupport.setCode(saveMerchantResult.getErrorCode());
				resultSupport.setMsg(saveMerchantResult.getErrorMsg());
				resultSupport.setSuccess(false);
				
			}
		} catch (Exception e) {
			log.error("add merchant of food error and params:MerchantVO={}"+JSON.toJSONString(vo)+"and exception:{} "+e);
			resultSupport.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
			
		}
		return resultSupport;
		
		
	}
	public BizResultSupport updateDeliciousFood(MerchantVO vo) {
		BizResultSupport resultSupport = new BizResultSupport();
		if (vo == null) {
			log.error("params is "+JSON.toJSONString(vo));
			resultSupport.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return resultSupport;
		}
		try {
			BaseResult<Boolean> updateMerchantResult = userMerchantServiceRef.updateMerchantInfo(vo.getMerchantDTO(vo));
			if (updateMerchantResult == null) {
				resultSupport.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
				return resultSupport;
			}
			if (!updateMerchantResult.isSuccess()) {
				log.error("result :{} "+JSON.toJSONString(updateMerchantResult));
				resultSupport.setCode(updateMerchantResult.getErrorCode());
				resultSupport.setMsg(updateMerchantResult.getErrorMsg());
				resultSupport.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("update merchant of food error and params:MerchantVO={}"+JSON.toJSONString(vo)+"and exception is "+e);
			resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
			
		}
		return resultSupport;
		
	}
	public BizResultSupport batchUpdateMerchant(List<Long> idList,int status) {
		BizResultSupport bizSupport = new BizResultSupport();
		if (idList == null || status <= 0) {
			log.error("params is:idList={} status= "+JSON.toJSONString(idList),status);
			bizSupport.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return bizSupport;
		}
		try {
			BaseResult<Boolean> updateResult = userMerchantServiceRef.updateMerchantListStatus(idList, status);
			if (updateResult == null || !updateResult.isSuccess()) {
				bizSupport.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
				return bizSupport;
			}else if (!updateResult.isSuccess()) {
				bizSupport.setCode(updateResult.getErrorCode());
				bizSupport.setMsg(updateResult.getErrorMsg());
				bizSupport.setSuccess(false);
			}
		} catch (Exception e) {
			log.error("batch update merchant of food  error and params:idList={}"+JSON.toJSONString(idList)+"and status ="+status);
			bizSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
		}
		return bizSupport;
		
	}
	
	public BaseResult<MerchantDO> getMerchantBySellerId(long userId) {
		if(userId <=0){
			return null;
		}
		try {
			BaseResult<MerchantDO> meResult = userMerchantServiceRef.getMerchantBySellerId(userId, Constant.DOMAIN_JIUXIU);
			if(null==meResult || !meResult.isSuccess() || null==meResult.getValue()){
				return null;
			}else{
				return meResult;
			}
		} catch (Exception e) {
			log.error("getMerchantBySellerId error and userId="+userId);
			return null;
		}
	}
	
	public BizResult<List<CertificatesDO>> getServiceTypes() {
		BizResult<List<CertificatesDO>> bizResult = new BizResult<List<CertificatesDO>>();
		try {
			MemResult<List<CertificatesDO>> serviceTypes = talentInfoDealService.queryTalentServiceType();
			if (serviceTypes == null || !serviceTypes.isSuccess() || null==serviceTypes.getValue()) {
				bizResult.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
				//log.error("params :");
				return bizResult;
			}else{
				bizResult.setValue(serviceTypes.getValue());
				return bizResult;
			}
		} catch (Exception e) {
			log.error("queryTalentServiceType error");
			return bizResult;
		}
	}
	
	public BizResult<TalentInfoDTO> queryTalentInfoByUserId(long userId,int domainId) {
		BizResult<TalentInfoDTO> bizResult = new BizResult<TalentInfoDTO>();
		if (userId <= 0 || domainId <= 0) {
			log.error("params error : userId={},domainId={}",userId,domainId);
			return null;
		}
		try {
			MemResult<TalentInfoDTO> queryResult = talentInfoDealService.queryTalentInfoByUserId(userId, domainId);
			if (queryResult == null || !queryResult.isSuccess() || null==queryResult.getValue()) {
				bizResult.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
				log.error("params : userId={},domainId={} error:{}",userId,domainId);
				return bizResult;
			}else{
				bizResult.setValue(queryResult.getValue());
				return bizResult;
			}
		} catch (Exception e) {
			log.error("params : userId={},domainId={} error:{}",userId,domainId,e);
			return bizResult;
		}
	}
	
}
