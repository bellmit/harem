/**  
 * Project Name:palace-biz  
 * File Name:EatMerchantVO.java  
 * Package Name:com.yimayhd.palace.model  
 * Date:2016年3月24日上午10:53:42  
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.  
 *  
*/

package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:MerchantVO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年3月24日 上午10:53:42 <br/>
 * 
 * @author zhangjian
 * @version
 * @see
 */
public class EatMerchantVO implements Serializable{
	
	/**  
	 * serialVersionUID:
	 */
	
	private static final long serialVersionUID = -1671129382178013317L;
	private Long id;
	private String name;
	private List<String> loopImages;
	private String contactTel;
	private String ServiceTime;
	private Long avgPay;
	private List<String> serviceFacilities;
	private String cityName;
	private int cityCode;
	private String address;
	private Double lon;
	private Double lat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLoopImages() {
		return loopImages;
	}

	public void setLoopImages(List<String> loopImages) {
		this.loopImages = loopImages;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getServiceTime() {
		return ServiceTime;
	}

	public void setServiceTime(String serviceTime) {
		ServiceTime = serviceTime;
	}

	public Long getAvgPay() {
		return avgPay;
	}

	public void setAvgPay(Long avgPay) {
		this.avgPay = avgPay;
	}

	public List<String> getServiceFacilities() {
		return serviceFacilities;
	}

	public void setServiceFacilities(List<String> serviceFacilities) {
		this.serviceFacilities = serviceFacilities;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

}
