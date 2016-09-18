package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522270147472331376L;
	private String name;// 酒店名称
	private Integer hotelStatus;// 状态
	private long regionId;// 区域ID
	private String regionName;// 区域名称
	private String hotelNameOrTel;// 酒店联系电话
	private String beginDate;// 开始创建时间
	private String endDate;// 结束创建时间
	private Integer pageBegin;
	private int domain;
	private Long locationProvinceId;
	private Long locationCityId;
	private Integer type;

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHotelNameOrTel() {
		return hotelNameOrTel;
	}

	public void setHotelNameOrTel(String hotelNameOrTel) {
		this.hotelNameOrTel = hotelNameOrTel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getHotelStatus() {
		return hotelStatus;
	}

	public void setHotelStatus(Integer hotelStatus) {
		this.hotelStatus = hotelStatus;
	}

	public Integer getPageBegin() {
		return pageBegin;
	}

	public void setPageBegin(Integer pageBegin) {
		this.pageBegin = pageBegin;
	}
	public Long getLocationProvinceId() {
		return locationProvinceId;
	}

	public void setLocationProvinceId(Long locationProvinceId) {
		this.locationProvinceId = locationProvinceId;
	}

	public Long getLocationCityId() {
		return locationCityId;
	}

	public void setLocationCityId(Long locationCityId) {
		this.locationCityId = locationCityId;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
