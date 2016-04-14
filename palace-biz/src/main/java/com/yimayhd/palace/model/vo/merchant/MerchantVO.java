package com.yimayhd.palace.model.vo.merchant;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.glassfish.grizzly.utils.ServiceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.enums.ServiceFacilityOption;

/**
 * 
 * @author zhangxy
 *
 */
public class MerchantVO extends MerchantDTO {

	protected Logger log = LoggerFactory.getLogger(getClass());
	private String loopImageStr;
	
	public String getLoopImageStr() {
		return loopImageStr;
	}
	public void setLoopImageStr(String loopImageStr) {
		this.loopImageStr = loopImageStr;
	}
	private String service;//服务设施
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public MerchantDTO getMerchantDTO(MerchantVO vo,long userId) {
		MerchantDTO dto =  new MerchantDTO();
		try {
			BeanUtils.copyProperties(dto, vo);
			dto.setDomainId(Constant.DOMAIN_JIUXIU);
			dto.setId(userId);
			dto.setLoopImages(JSON.parseArray(vo.getLoopImageStr(), String.class));
			List<String> serviceList = JSON.parseArray(vo.getService(), String.class);
			List<ServiceFacilityOption> facilityList = new ArrayList<ServiceFacilityOption>();
			for (String str : serviceList) {
				ServiceFacilityOption facilityOption = ServiceFacilityOption.valueOfCode(str);
				//facilityOption.setOption(option);
				facilityList.add(facilityOption);
			}
			if (ServiceFacilityOption.addOption(facilityList) > 0) {
				dto.setServiceFacility(ServiceFacilityOption.addOption(facilityList));
			}
			//dto.setServiceFacility(ServiceFacilityOption.addOption());
		} catch (Exception e) {
			log.error("copy deliciousFoodVO to merchantDTO error and params:"+JSON.toJSONString(vo));
			return null;
		}
		return dto;
		
	}
	public MerchantDO getMerchantDO(MerchantVO vo,long userId) {
		MerchantDO merchantDO = new MerchantDO();
		try {
			merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
			merchantDO.setId(userId);
			//merchantDO.s
			BeanUtils.copyProperties(merchantDO, vo);
			merchantDO.setLoopImages(JSON.parseArray(vo.getLoopImageStr(), String.class));
			List<String> serviceList = JSON.parseArray(vo.getService(), String.class);
			List<ServiceFacilityOption> facilityList = new ArrayList<ServiceFacilityOption>();
			for (String str : serviceList) {
				ServiceFacilityOption facilityOption = ServiceFacilityOption.valueOfCode(str);
				//facilityOption.setOption(option);
				facilityList.add(facilityOption);
			}
			if (ServiceFacilityOption.addOption(facilityList) > 0) {
				merchantDO.setServiceFacility(ServiceFacilityOption.addOption(facilityList));
			}
		} catch (Exception e) {
			log.error("copy diliciousFoodVO to merchantDO error and params:"+JSON.toJSONString(vo));
			return null;
		}
		return merchantDO;
		
	}
	
}
