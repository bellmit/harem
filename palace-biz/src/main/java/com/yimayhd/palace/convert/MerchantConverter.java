/**  
 * Project Name:palace-biz  
 * File Name:MerchantConverter.java  
 * Package Name:com.yimayhd.palace.convert  
 * Date:2016年3月24日下午2:48:29  
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.  
 *  
*/  
  
package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.palace.model.EatMerchantVO;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.ServiceFacilityOption;

/**  
 * ClassName:MerchantConverter <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年3月24日 下午2:48:29 <br/>  
 * @author   zhangjian  
 * @version    
 * @see        
 */
public class MerchantConverter {
	public static MerchantDTO convertEatMerchantVOToMerchantDTO(EatMerchantVO eatMerchantVO){
		if(eatMerchantVO == null){
			return null;
		}
		
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setId(eatMerchantVO.getId());
		merchantDTO.setLoopmages(eatMerchantVO.getLoopImages());
		merchantDTO.setServiceTel(eatMerchantVO.getContactTel());
		merchantDTO.setServiceTime(eatMerchantVO.getServiceTime());
		merchantDTO.setAvgprice(eatMerchantVO.getAvgPay());
		
		List<ServiceFacilityOption> optionList = new ArrayList<ServiceFacilityOption>();
		if(!CollectionUtils.isEmpty(eatMerchantVO.getServiceFacilities())){
			for(String serviceFacility : eatMerchantVO.getServiceFacilities()){
				ServiceFacilityOption option = ServiceFacilityOption.valueOfCode(serviceFacility);
				if(option != null){
					optionList.add(option);
				}
			}
		}
		merchantDTO.setServiceFacility(ServiceFacilityOption.addOption(optionList));
		
		merchantDTO.setCityCode(eatMerchantVO.getCityCode());
		merchantDTO.setCityName(eatMerchantVO.getCityName());
		merchantDTO.setAddress(eatMerchantVO.getAddress());
		merchantDTO.setLat(eatMerchantVO.getLat());
		merchantDTO.setLon(eatMerchantVO.getLon());
		
		return merchantDTO;
		
	}
	
	public static MerchantDO convertEatMerchantVOToMerchantDO(EatMerchantVO eatMerchantVO){
		if(eatMerchantVO == null){
			return null;
		}
		
		MerchantDO merchantDO = new MerchantDO();
		merchantDO.setId(eatMerchantVO.getId());
		merchantDO.setLoopImages(eatMerchantVO.getLoopImages());
		merchantDO.setServiceTel(eatMerchantVO.getContactTel());
		merchantDO.setServiceTime(eatMerchantVO.getServiceTime());
		merchantDO.setAvgprice(eatMerchantVO.getAvgPay());
		
		List<ServiceFacilityOption> optionList = new ArrayList<ServiceFacilityOption>();
		if(!CollectionUtils.isEmpty(eatMerchantVO.getServiceFacilities())){
			for(String serviceFacility : eatMerchantVO.getServiceFacilities()){
				ServiceFacilityOption option = ServiceFacilityOption.valueOfCode(serviceFacility);
				if(option != null){
					optionList.add(option);
				}
			}
		}
		merchantDO.setServiceFacility(ServiceFacilityOption.addOption(optionList));
		
		merchantDO.setCityCode(eatMerchantVO.getCityCode());
		merchantDO.setCityName(eatMerchantVO.getCityName());
		merchantDO.setAddress(eatMerchantVO.getAddress());
		merchantDO.setLat(eatMerchantVO.getLat());
		merchantDO.setLon(eatMerchantVO.getLon());
		
		return merchantDO;
	}
	
	public static EatMerchantVO convertMerchantUserDTOToEatMerchantVO(MerchantUserDTO merchantDTO){
		if(merchantDTO == null){
			return null;
		}
		
		EatMerchantVO eatMerchantVO = new EatMerchantVO();
//		private Long id;
//		private String name;
//		private List<String> loopImages;
//		private String contactTel;
//		private String ServiceTime;
//		private Long avgPay;
//		private List<String> serviceFacilities;
//		private String cityName;
//		private int cityCode;
//		private String address;
//		private Double lon;
//		private Double lat;
		MerchantDO  merchantDO = merchantDTO.getMerchantDO();
		eatMerchantVO.setId(merchantDO.getId());
		eatMerchantVO.setName(merchantDO.getName());
		eatMerchantVO.setLoopImages(merchantDO.getLoopImages());
		eatMerchantVO.setContactTel(merchantDO.getServiceTel());
		eatMerchantVO.setServiceTime(merchantDO.getServiceTime());
		eatMerchantVO.setAvgPay(merchantDO.getAvgprice());
		List<String> serviceFacilityCodes = new ArrayList<String>();
		for(ServiceFacilityOption serviceFacility : ServiceFacilityOption.getContainedOptions(merchantDO.getServiceFacility())){
			serviceFacilityCodes.add(serviceFacility.getCode());
		}
		eatMerchantVO.setServiceFacilities(serviceFacilityCodes);
		eatMerchantVO.setCityName(merchantDO.getCityName());
		eatMerchantVO.setCityCode(merchantDO.getCityCode());
		eatMerchantVO.setAddress(merchantDO.getAddress());
		eatMerchantVO.setLat(merchantDO.getLat());
		eatMerchantVO.setLon(merchantDO.getLon());
		
		return eatMerchantVO;
	}
}	
  
