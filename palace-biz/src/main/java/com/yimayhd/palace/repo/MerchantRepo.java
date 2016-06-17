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
		if (vo == null) {
			return null;
		}
		BizResultSupport resultSupport = new BizResultSupport();
		try {
			log.error("params is "+vo);
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setRegisterType(RegisterType.NO_ACCOUNT);
			registerDTO.setDomainId(Constant.DOMAIN_JIUXIU);
			BaseResult<UserDO> registerWithoutAccount = userServiceRef.registerWithoutAccount(registerDTO);
			if (registerWithoutAccount == null || !registerWithoutAccount.isSuccess() || registerWithoutAccount.getValue() == null) {
				log.error("generate merchant's id of food error");
				throw new BaseException("生产注册美食商家id失败");
			}
			BaseResult<MerchantDO> saveMerchantResult = userMerchantServiceRef.saveMerchant(vo.getMerchantDO(vo,registerWithoutAccount.getValue().getId()));
			log.error("error is "+saveMerchantResult);
			if (saveMerchantResult == null) {
				resultSupport.init(false, -1, "保存失败");
				return resultSupport;
			}
			log.error("info is "+saveMerchantResult.getErrorMsg()+saveMerchantResult.getErrorCode()+saveMerchantResult.isSuccess());
			log.error("info is "+resultSupport);
			if (!saveMerchantResult.isSuccess()) {
				log.error("info is "+saveMerchantResult.getErrorMsg()+saveMerchantResult.getErrorCode()+saveMerchantResult.isSuccess());
				log.error("info is "+resultSupport);
				resultSupport.init(false, saveMerchantResult.getErrorCode(), saveMerchantResult.getErrorMsg());
				//resultSupport.setPalaceReturnCode(PalaceReturnCode.ADD_MERCHANT_ERROR);
			}
			log.error("info is "+resultSupport);
			//return resultSupport;
		} catch (Exception e) {
			log.error("add merchant of food error and params:"+JSON.toJSONString(vo)+"and exception is "+e);
			resultSupport.setPalaceReturnCode(PalaceReturnCode.ADD_MERCHANT_ERROR);
			
		}
		return resultSupport;
		
		
	}
	public BizResultSupport updateDeliciousFood(MerchantVO vo) {
		if (vo == null) {
			return null;
		}
		BizResultSupport resultSupport = new BizResultSupport();
		try {
			//MerchantDTO dto = new MerchantDTO();
			//dto.sets
			log.error("params is "+vo);
			BaseResult<Boolean> updateMerchantResult = userMerchantServiceRef.updateMerchantInfo(vo.getMerchantDTO(vo));
			log.error("error is "+updateMerchantResult);
			if (updateMerchantResult == null) {
				resultSupport.init(false, -1, "保存失败");
				return resultSupport;
			}
			log.error("info is "+updateMerchantResult.getErrorMsg()+updateMerchantResult.getErrorCode()+updateMerchantResult.isSuccess());
			log.error("info is "+resultSupport);
			if (!updateMerchantResult.isSuccess()) {
				log.error("info is "+updateMerchantResult.getErrorMsg()+updateMerchantResult.getErrorCode()+updateMerchantResult.isSuccess());
				log.error("info is "+resultSupport);
				//resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
				resultSupport.init(false, updateMerchantResult.getErrorCode(), updateMerchantResult.getErrorMsg());
			}
			log.error("info is "+resultSupport);
			//return resultSupport;
		} catch (Exception e) {
			log.error("update merchant of food error and params:"+JSON.toJSONString(vo)+"and exception is "+e.getMessage());
			resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
			
		}
		return resultSupport;
		
	}
	public BizResultSupport batchUpdateMerchant(List<Long> idList,int status) {
		if (idList == null || status <= 0) {
			return null;
		}
		BizResultSupport bizSupport = new BizResultSupport();
		try {
			BaseResult<Boolean> updateResult = userMerchantServiceRef.updateMerchantListStatus(idList, status);
			if (!updateResult.isSuccess()) {
				bizSupport.init(false, updateResult.getErrorCode(), updateResult.getErrorMsg());
			}
			return bizSupport;
		} catch (Exception e) {
			log.error("batch update merchant of food  error and params:"+JSON.toJSONString(idList)+"and status ="+status);
			return null;
		}
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
