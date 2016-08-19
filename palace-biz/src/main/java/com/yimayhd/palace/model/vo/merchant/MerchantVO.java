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
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
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
	public static final  double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	private String merchantName;
	private double longitude;
	private double latitude;
	private String openTime;
	private String merchantAddress;
	private String pictureTextString;
	private PictureTextVO pictureText;
	
	public PictureTextVO getPictureText() {
		return pictureText;
	}
	public void setPictureText(PictureTextVO pictureText) {
		this.pictureText = pictureText;
	}
	public String getPictureTextString() {
		return pictureTextString;
	}
	public void setPictureTextString(String pictureTextString) {
		this.pictureTextString = pictureTextString;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getMerchantAddress() {
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}
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
			throw new BaseException("参数错误");
		}
		MerchantDTO dto =  new MerchantDTO();
		try {
			BeanUtils.copyProperties(dto, vo);
			
			dto.setDomainId(Constant.DOMAIN_JIUXIU);
			dto.setName(filterBlankChar(vo.getMerchantName()));
			dto.setAddress(filterBlankChar(vo.getMerchantAddress()));
			dto.setServiceTime(filterBlankChar(vo.getOpenTime()));

			bd_decrypt(vo.getLatitude(), vo.getLongitude(),dto);

			dto.setAvgprice(Math.round(vo.getAveragePrice()*100));
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
	
	private String filterBlankChar(String str) {
		if (str == null) {
			return "";
		}
		return StringHelper.replaceBlank(str);
	}
	
	
	public MerchantDO getMerchantDO(MerchantVO vo,long userId) {
		if (vo == null || userId <= 0 ) {
			log.error("merchantVO get merchantDO error and params:vo={}"+JSON.toJSONString(vo)+"and userId="+userId);
			throw new BaseException("参数错误");
		}
		MerchantDO merchantDO = new MerchantDO();
		try {
			BeanUtils.copyProperties(merchantDO, vo);
			merchantDO.setAddress(filterBlankChar(vo.getMerchantAddress()));
			merchantDO.setServiceTime(filterBlankChar(vo.getOpenTime()));
			merchantDO.setName(filterBlankChar(vo.getMerchantName()));
			merchantDO.setDomainId(Constant.DOMAIN_JIUXIU);
			merchantDO.setSellerId(userId);

			bd_decrypt(vo.getLatitude(), vo.getLongitude(),merchantDO);


			merchantDO.setAvgprice(Math.round(vo.getAveragePrice()*100));
			merchantDO.setStatus(MerchantStatus.OFFLINE.getCode());
			merchantDO.setOption(MerchantOption.EAT.getOption());
			List<String> imgList = JSON.parseArray(vo.getLoopImageStr(), String.class);
			merchantDO.setMerchantPrincipalTel(vo.getMerchantPrincipalTel());
			if (imgList != null && imgList.size()>0) {
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
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	 /**
	  * 将GCJ-02坐标系转换成BD-02坐标系
	  * @param gg_lat
	  * @param gg_lon
	  * @param bd_lat
	  * @param bd_lon
	  */
	public void bd_encrypt(double gg_lat, double gg_lon,MerchantDO merchantDO) {
	    double x = gg_lon, y = gg_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	    merchantDO.setLon(z * Math.cos(theta) + 0.0065);
	    merchantDO.setLat(z * Math.sin(theta) + 0.006);
	}
	/**
	  * 将BD-09坐标系转换成GCJ-02坐标系
	  * @param bd_lat
	  * @param bd_lon
	  * @param gg_lat
	  * @param gg_lon
	  */
	public void bd_decrypt(double latitude, double longitude,Object obj) {
	    double x = longitude - 0.0065, y = latitude - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	    if (obj instanceof MerchantDO) {
			MerchantDO merchantDO = (MerchantDO)obj;
			merchantDO.setLon(z * Math.cos(theta));
			merchantDO.setLat(z * Math.sin(theta));
		}
	    if (obj instanceof MerchantDTO) {
	    	MerchantDTO dto = (MerchantDTO)obj;
	    	dto.setLon(z * Math.cos(theta));
	    	dto.setLat(z * Math.sin(theta));
	    }
	}
public static void main(String[] args) {
	System.out.println(MerchantOption.EAT.getOption());
}
	
}
