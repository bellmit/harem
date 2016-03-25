/**  
 * Project Name:palace-biz  
 * File Name:EatMerchantServiceImpl.java  
 * Package Name:com.yimayhd.palace.service.impl  
 * Date:2016年3月24日上午11:19:33  
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.  
 *  
*/  
  
package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.MerchantConverter;
import com.yimayhd.palace.model.EatMerchantVO;
import com.yimayhd.palace.model.query.EatMerchantListQuery;
import com.yimayhd.palace.service.EatMerchantService;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.MerchantStatus;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;

/**  
 * ClassName:EatMerchantServiceImpl <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年3月24日 上午11:19:33 <br/>  
 * @author   zhangjian  
 * @version    
 * @see        
 */
public class EatMerchantServiceImpl implements EatMerchantService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EatMerchantServiceImpl.class);
	
	@Autowired
	private MerchantService merchantServiceRef;

	@Override
	public EatMerchantVO addEatMerchant(EatMerchantVO eatMerchantVO) {
		MerchantDO merchantDO = MerchantConverter.convertEatMerchantVOToMerchantDO(eatMerchantVO);
		BaseResult<MerchantDO> addResult = merchantServiceRef.saveMerchant(merchantDO);
		if(addResult.isSuccess()){
			eatMerchantVO.setId(addResult.getValue().getId());
			return eatMerchantVO;
		}else{
			LOGGER.error("merchantServiceRef.addResult error:eatMerchantVO={},updateResult={}",JSONObject.toJSONString(eatMerchantVO),JSONObject.toJSONString(addResult));
			throw new BaseException(addResult.getErrorMsg());
		}
	
	}

	@Override
	public boolean updateEatMerchant(EatMerchantVO eatMerchantVO) {
		MerchantDTO merchantDTO = MerchantConverter.convertEatMerchantVOToMerchantDTO(eatMerchantVO);
		BaseResult<Boolean> updateResult  = merchantServiceRef.updateMerchantInfo(merchantDTO);
		if(!updateResult.isSuccess()){
			LOGGER.error("merchantServiceRef.updateMerchantInfo error:eatMerchantVO={},updateResult={}",JSONObject.toJSONString(eatMerchantVO),JSONObject.toJSONString(updateResult));
			throw new BaseException(updateResult.getErrorMsg());
		}
		
		return true;
	}

	@Override
	public boolean batchDeleteEatMerchant(List<Long> eatMerchantIds) {
		BaseResult<Boolean> updateResult = merchantServiceRef.updateMerchantListStatus(eatMerchantIds, MerchantStatus.INVALID.getCode());
		if(!updateResult.isSuccess()){
			LOGGER.error("merchantServiceRef.updateMerchantListStatus error:eatMerchantIds={},updateResult={}",JSONObject.toJSONString(eatMerchantIds),JSONObject.toJSONString(updateResult));
			throw new BaseException(updateResult.getErrorMsg());
		}
		return false;
	}

	@Override
	public boolean batchOfflineEatMerchant(List<Long> eatMerchantIds) {
		  
		BaseResult<Boolean> updateResult = merchantServiceRef.updateMerchantListStatus(eatMerchantIds, MerchantStatus.OFFLINE.getCode());
		if(!updateResult.isSuccess()){
			LOGGER.error("merchantServiceRef.updateMerchantListStatus error:eatMerchantIds={},updateResult={}",JSONObject.toJSONString(eatMerchantIds),JSONObject.toJSONString(updateResult));
			throw new BaseException(updateResult.getErrorMsg());
		}
		return false;
	}
	
	@Override
	public boolean batchOnlineEatMerchant(List<Long> eatMerchantIds) {
		  
		BaseResult<Boolean> updateResult = merchantServiceRef.updateMerchantListStatus(eatMerchantIds, MerchantStatus.ONLINE.getCode());
		if(!updateResult.isSuccess()){
			LOGGER.error("merchantServiceRef.updateMerchantListStatus error:eatMerchantIds={},updateResult={}",JSONObject.toJSONString(eatMerchantIds),JSONObject.toJSONString(updateResult));
			throw new BaseException(updateResult.getErrorMsg());
		}
		return false;
	}

	@Override
	public PageVO<EatMerchantVO> queryEatMerchantList(EatMerchantListQuery eatMerchantListQuery) {
		MerchantPageQuery merchantPageQuery = new MerchantPageQuery();
		merchantPageQuery.setDomainId(eatMerchantListQuery.getDomainId());
		merchantPageQuery.setNeedCount(true);
		merchantPageQuery.setPageNo(eatMerchantListQuery.getPageNumber());
		merchantPageQuery.setPageSize(eatMerchantListQuery.getPageSize());
		
		BasePageResult<MerchantUserDTO> pageResult = merchantServiceRef.getMerchantUserList(merchantPageQuery);
		if(!pageResult.isSuccess()){
			LOGGER.error("merchantServiceRef.getMerchantUserList error:merchantPageQuery={},pageResult={}",JSONObject.toJSONString(merchantPageQuery),JSONObject.toJSONString(pageResult));
			throw new BaseException(pageResult.getErrorMsg());
		}
		
		PageVO<EatMerchantVO> pageVO  = new PageVO<EatMerchantVO>(eatMerchantListQuery.getPageNumber(), eatMerchantListQuery.getPageSize(), pageResult.getTotalCount()) ;
		List<EatMerchantVO> voList = new ArrayList<EatMerchantVO>();
		if(!CollectionUtils.isEmpty(pageResult.getList())){
			for(MerchantUserDTO merchantDTO : pageResult.getList()){
				voList.add(MerchantConverter.convertMerchantUserDTOToEatMerchantVO(merchantDTO));
			}
		}
		pageVO.setItemList(voList);
		
		return pageVO;
	}

	

}
  
