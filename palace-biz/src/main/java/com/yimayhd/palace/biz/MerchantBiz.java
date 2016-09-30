package com.yimayhd.palace.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.HotelPublishDTO;
import com.yimayhd.ic.client.model.param.item.SetScenicWeightDTO;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.SetMerchantWeightDTO;
import com.yimayhd.user.client.query.MerchantQuery;
import com.yimayhd.user.client.result.BaseResult;

/**
 * 
 * @author zhangxy
 *
 */
public class MerchantBiz {
	
	private static final Logger log = LoggerFactory.getLogger("MerchantBiz");
	@Autowired
	private MerchantRepo merchantRepo;
	public BizResultSupport addDeliciousFood(MerchantVO vo) {
		return merchantRepo.addDeliciousFood(vo);
		
		
	}
	
	public BizResultSupport updateDeliciousFood(MerchantVO vo) {
		return merchantRepo.updateDeliciousFood(vo);
	}
	public BizResultSupport batchUpdateMerchant(List<Long> idList,int status) {
		return merchantRepo.batchUpdateMerchant(idList, status);
	}
	
	public BaseResult<MerchantDO> getMerchantBySellerId(long userId){
		return merchantRepo.getMerchantBySellerId(userId);
	}
	
	/**
	 * 获取达人基本信息的服务类型
	 * @return 
	 * @return
	 */
	public  BizResult<List<CertificatesDO>> getServiceTypes() {
		return  merchantRepo.getServiceTypes();
	}
	
	public BizResult<TalentInfoDTO> queryTalentInfoByUserId (long userId,int domainId) {
		return merchantRepo.queryTalentInfoByUserId(userId, domainId);
	}
	
	public PictureTextVO getPictureText(long id) {
		PictureTextVO pictureTextVO = null;
		try {

			log.info("==============================id"+id);
			PicTextResult picTextResult = merchantRepo.getPictureText(id);
			log.info("=============================="+JSON.toJSONString(picTextResult));

			pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
		} catch (Exception e) {
			log.error("params:id={} ,exception:{}",id,e);
		}
		return pictureTextVO; 
	}
	
	public BizResult<Boolean> modifyMerchantWeight(long merchantId,int weightValue) {
		BizResult<Boolean> result = new BizResult<Boolean>();
		SetMerchantWeightDTO dto = new SetMerchantWeightDTO();
		dto.setId(merchantId);
		dto.setOrderNum(weightValue);
		boolean setResult = merchantRepo.modifyMerchantWeight(dto);
		if (!setResult) {
			result.setPalaceReturnCode(PalaceReturnCode.UPDATE_WEIGHT_FAILED);
			return result;
		}
		return result;
		
	}
	/*public BizResult<Boolean> modifyHotelWeight(long itemId,int weightValue) {
		BizResult<Boolean> result = new BizResult<Boolean>();
		HotelPublishDTO dto = new HotelPublishDTO();
		dto.setSort(weightValue);
		ItemDO itemDO = new ItemDO();
		itemDO.setId(itemId);
		dto.setItemDO(itemDO);
		boolean setResult = merchantRepo.modifyHotelWeight(dto);
		if (!setResult) {
			result.setPalaceReturnCode(PalaceReturnCode.UPDATE_WEIGHT_FAILED);
			return result;
		}
		return result;
		
	}
	public BizResult<Boolean> modifyScenicWeight(long itemId,int weightValue) {
		BizResult<Boolean> result = new BizResult<Boolean>();
		SetScenicWeightDTO dto = new SetScenicWeightDTO();
		dto.setId(itemId);
		dto.setOrderNum(weightValue);
		boolean setResult = merchantRepo.modifyScenicWeight(dto);
		if (!setResult) {
			result.setPalaceReturnCode(PalaceReturnCode.UPDATE_WEIGHT_FAILED);
			return result;
		}
		return result;
		
	}*/
	
	public BizResult<List<Long>> getUserIds(String sellerName,String merchantName) {
		BizResult<List<Long>> result = new BizResult<List<Long>>();
		if (StringUtils.isBlank(sellerName)) {
			return result;
		}
		List<Long> userIdList = null;
		if ("shopName".equalsIgnoreCase(merchantName)) {
			userIdList = getUserIdsByShopName(sellerName);
		}else if ("nickName".equalsIgnoreCase(merchantName)) {
			userIdList = getUserIdsByNickName(sellerName);
		}
		result.setValue(userIdList);
		return result;
	}
	
	private List<Long> getUserIdsByNickName(String name) {
		BaseResult<List<UserDO>> userIdsResult = merchantRepo.getUserIds(name.trim());
		if (userIdsResult == null || CollectionUtils.isEmpty(userIdsResult.getValue())) {
			log.error("get userIds fail,result:{}",JSON.toJSONString(userIdsResult));
//			result.setPalaceReturnCode(PalaceReturnCode.QUERY_FAILED);
//			return result;	
			return null;
		}
		List<UserDO> userDOs = userIdsResult.getValue();
		List<Long> userIdsList = new ArrayList<Long>();
		for (UserDO userDO : userDOs) {
			userIdsList.add(userDO.getId());
		}
		return userIdsList;
	}
	private List<Long> getUserIdsByShopName(String name) {
		MerchantQuery merchantQuery = new MerchantQuery();
		merchantQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantQuery.setName(name.trim());
		BaseResult<List<MerchantDO>> userIdsResult = merchantRepo.getUserIds(merchantQuery);
		if (userIdsResult == null || CollectionUtils.isEmpty(userIdsResult.getValue())) {
			log.error("get userIds fail,result:{}",JSON.toJSONString(userIdsResult));
			return null;
		}
		List<MerchantDO> merchantDOs = userIdsResult.getValue();
		List<Long> userIdsList = new ArrayList<Long>();
		for (MerchantDO merchant : merchantDOs) {
			userIdsList.add(merchant.getSellerId());
		}
		return userIdsList;
	}
}

