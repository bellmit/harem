package com.yimayhd.palace.model.vo.merchant;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.utils.ServiceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.helper.apply.StringHelper;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.enums.MerchantStatus;
import com.yimayhd.user.client.enums.ServiceFacilityOption;

/**
 * 
 * @author zhangxy
 *
 */
public class MerchantVO extends MerchantDTO {

	protected Logger log = LoggerFactory.getLogger(getClass());
	private String merchantName;
//	private String cityInfo;
//	public String[] getCityInfo() {
//		String[] cityInfoArr = cityInfo.split(",");
//		return cityInfoArr;
//	}
//	public void setCityInfo(String cityInfo) {
//		this.cityInfo = cityInfo;
//	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	private String loopImageStr;
	
	public String getLoopImageStr() {
		return loopImageStr;
	}
	public void setLoopImageStr(String loopImageStr) {
		this.loopImageStr = loopImageStr;
	}
	private String service;//服务设施
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public double averagePrice;
	
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
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
			String name = getName(vo.getMerchantName());
			dto.setName(name);
//			dto.setCityCode(Integer.parseInt(getCityInfo()[0]));
//			dto.setCityName(getCityInfo()[0]);
			//dto.setId(userId);
			dto.setAvgprice(getAvgPrice(vo.getAveragePrice()));
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
	private long getAvgPrice(double averagePrice) {
		long l = (long)averagePrice;
		DecimalFormat df = new DecimalFormat(".##");
		long f = (long)Double.parseDouble(df.format((averagePrice-l)*100));
		//long f =(long)( (Double.parseDouble(df.format(averagePrice))-l)*100);
		return (l*100+f);
	}
	
	private String getName(String merchantName) {
		if (merchantName == null) {
			return "";
		}
		return StringHelper.replaceBlank(merchantName);
	}
	
	
	public MerchantDO getMerchantDO(MerchantVO vo,long userId) {
		if (vo == null || userId <= 0 ) {
			log.error("merchantVO get merchantDO error and params:vo={}"+JSON.toJSONString(vo)+"and userId="+userId);
			throw new BaseException("参数错误");
		}
		MerchantDO merchantDO = new MerchantDO();
		try {
			BeanUtils.copyProperties(merchantDO, vo);
//			merchantDO.setCityCode(Integer.parseInt(getCityInfo()[0]));
//			merchantDO.setCityName(getCityInfo()[0]);
			String name = getName(vo.getMerchantName());
			merchantDO.setName(name);
			merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
			merchantDO.setSellerId(userId);
			merchantDO.setAvgprice(getAvgPrice(vo.getAveragePrice()));
			//merchantDO.setLoopImages(JSON.parseArray(vo.getLoopImageStr(), String.class));
			merchantDO.setStatus(MerchantStatus.OFFLINE.getCode());
			merchantDO.setOption(MerchantOption.EAT.getOption());
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
