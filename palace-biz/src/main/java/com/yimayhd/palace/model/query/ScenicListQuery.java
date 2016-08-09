package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522270147472331376L;
		
	private String name;

  	private Integer status;

    private Integer level;

    private Long regionId;

    private Integer itemStatus;

    private Long subjectId;
    
	private String tags;
    
    private String startTime;
    
    private String endTime;
    
    private Long locationProvinceId;

	private Long locationCityId;
	
	private int domain;
		
	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	    
}
