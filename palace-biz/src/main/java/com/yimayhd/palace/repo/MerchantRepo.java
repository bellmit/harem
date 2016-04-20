package com.yimayhd.palace.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
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
	
	public BizResultSupport addDeliciousFood(MerchantVO vo) {
		if (vo == null) {
			return null;
		}
		BizResultSupport resultSupport = new BizResultSupport();
		try {
			BaseResult<MerchantDO> saveMerchantResult = userMerchantServiceRef.saveMerchant(vo.getMerchantDO(vo,sessionManager.getUserId()));
			log.error("error is "+saveMerchantResult);
			if (saveMerchantResult == null) {
				resultSupport.init(false, -1, "保存失败");
				return resultSupport;
			}
			if (!saveMerchantResult.isSuccess()) {
				resultSupport.init(false, saveMerchantResult.getErrorCode(), saveMerchantResult.getErrorMsg());
				//resultSupport.setPalaceReturnCode(PalaceReturnCode.ADD_MERCHANT_ERROR);
			}
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
			BaseResult<Boolean> updateMerchantResult = userMerchantServiceRef.updateMerchantInfo(vo.getMerchantDTO(vo));
			log.error("error is "+updateMerchantResult);
			if (updateMerchantResult == null) {
				resultSupport.init(false, -1, "保存失败");
				return resultSupport;
			}
			if (!updateMerchantResult.isSuccess()) {
				
				//resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
				resultSupport.init(false, updateMerchantResult.getErrorCode(), updateMerchantResult.getErrorMsg());
			}
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
	
}
