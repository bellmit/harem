package com.yimayhd.palace.biz;

import java.util.List;

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
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.SetMerchantWeightDTO;
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
}

