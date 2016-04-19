package com.yimayhd.palace.model.vo.merchant;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.glassfish.grizzly.utils.ServiceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.enums.MerchantStatus;
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
	public MerchantDTO getMerchantDTO(MerchantVO vo) {
		if (vo == null ) {
			log.error("merchantVO get merchantDTO error and params:vo={}"+JSON.toJSONString(vo));
			//return null;
			throw new BaseException("参数错误");
		}
		MerchantDTO dto =  new MerchantDTO();
		try {
			BeanUtils.copyProperties(dto, vo);
			dto.setDomainId(Constant.DOMAIN_JIUXIU);
			//dto.setId(userId);
			
			//dto.setLoopImages(JSON.parseArray(vo.getLoopImageStr(), String.class));
			List<String> imgList = JSON.parseArray(vo.getLoopImageStr(), String.class);
			if (imgList != null && imgList.size()>0) {
				
				dto.setLogoImage(imgList.get(0));
			}
			List<String> serviceList = JSON.parseArray(vo.getService(), String.class);
			List<ServiceFacilityOption> facilityList = new ArrayList<ServiceFacilityOption>();
			for (String str : serviceList) {
				ServiceFacilityOption facilityOption = ServiceFacilityOption.valueOfCode(str);
				facilityList.add(facilityOption);
			}
			if (ServiceFacilityOption.addOption(facilityList) > 0) {
				dto.setServiceFacility(ServiceFacilityOption.addOption(facilityList));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("copy deliciousFoodVO to merchantDTO error and params:"+JSON.toJSONString(vo));
			return null;
		}
		return dto;
		
	}
	public MerchantDO getMerchantDO(MerchantVO vo,long userId) {
		if (vo == null || userId <= 0 ) {
			log.error("merchantVO get merchantDO error and params:vo={}"+JSON.toJSONString(vo)+"and userId="+userId);
			throw new BaseException("参数错误");
		}
		MerchantDO merchantDO = new MerchantDO();
		try {
			BeanUtils.copyProperties(merchantDO, vo);
			merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
			merchantDO.setSellerId(userId);
			//merchantDO.setLoopImages(JSON.parseArray(vo.getLoopImageStr(), String.class));
			merchantDO.setStatus(MerchantStatus.INVALID.getCode());
			List<String> imgList = JSON.parseArray(vo.getLoopImageStr(), String.class);
			//merchantDO.setFeature("{merchantPrincipalTel:"+vo.getMerchantPrincipalTel()+"},{serviceTime:"+vo.getServiceTime()+"}");
			//merchantDO.setFeature(",{serviceTime:"+vo.getServiceTime()+"}");
			merchantDO.setMerchantPrincipalTel(vo.getMerchantPrincipalTel());
			merchantDO.setServiceTime(vo.getServiceTime());
			if (imgList != null && imgList.size()>0) {
				//merchantDO.setPicUrls(imgList.get(0));
				merchantDO.setLogo(imgList.get(0));
			}
			List<String> serviceList = JSON.parseArray(vo.getService(), String.class);
			List<ServiceFacilityOption> facilityList = new ArrayList<ServiceFacilityOption>();
			for (String str : serviceList) {
				ServiceFacilityOption facilityOption = ServiceFacilityOption.valueOfCode(str);
				facilityList.add(facilityOption);
			}
			if (ServiceFacilityOption.addOption(facilityList) > 0) {
				merchantDO.setServiceFacility(ServiceFacilityOption.addOption(facilityList));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("copy diliciousFoodVO to merchantDO error and params:"+JSON.toJSONString(vo));
			return null;
		}
		return merchantDO;
		
	}
	
}
