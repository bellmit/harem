package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
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
			if (!saveMerchantResult.isSuccess()) {
				
				resultSupport.setPalaceReturnCode(PalaceReturnCode.ADD_MERCHANT_ERROR);
			}
			return resultSupport;
		} catch (Exception e) {
			log.error("add merchant of food error and params:"+JSON.toJSONString(vo)+"and exception is "+e.getMessage());
			resultSupport.setPalaceReturnCode(PalaceReturnCode.ADD_MERCHANT_ERROR);
			
			return resultSupport;
		}
		
		
	}
	public BizResultSupport updateDeliciousFood(MerchantVO vo) {
		if (vo == null) {
			return null;
		}
		BizResultSupport resultSupport = new BizResultSupport();
		try {
			BaseResult<Boolean> updateMerchantResult = userMerchantServiceRef.updateMerchantInfo(vo.getMerchantDTO(vo, sessionManager.getUserId()));
			if (!updateMerchantResult.isSuccess()) {
				
				resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
			}
			return resultSupport;
		} catch (Exception e) {
			log.error("update merchant of food error and params:"+JSON.toJSONString(vo)+"and exception is "+e.getMessage());
			resultSupport.setPalaceReturnCode(PalaceReturnCode.UPDATE_MERCHANT_ERROR);
			
			return resultSupport;
		}
		
	}
	
}
