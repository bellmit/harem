package com.yimayhd.harem.model;

import java.io.Serializable;

public class RelevanceRecommended implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;//枚举的name
	private String descName;
	private int type;//类型，如景区，酒店 按枚举取
	private int cityCode;//关联的城市名称
	int[] resourceId;//数组类型的 关联的推荐类id
	
	
	public String getDescName() {
		return descName;
	}
	public void setDescName(String descName) {
		this.descName = descName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public int[] getResourceId() {
		return resourceId;
	}
	public void setResourceId(int[] resourceId) {
		this.resourceId = resourceId;
	}
	
	
	
	

}
