package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionLevel;

/**
 * Created by Administrator on 2015/11/13.
 */
public class Region extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String name;
	private Integer level;
	private Long parentId;

	public Region() {
	}

	public Region(RegionDO regionDO) {
		this.level = regionDO.getLevel();
		if (this.level == RegionLevel.PROVINCE.getLevel()) {
			this.code = regionDO.getProvinceCode();
			this.name = regionDO.getProvinceName();
		} else if (this.level == RegionLevel.CITY.getLevel()) {
			this.code = regionDO.getCityCode();
			this.name = regionDO.getCityName();
		} else if (this.level == RegionLevel.TOWN.getLevel()) {
			this.code = regionDO.getTownCode();
			this.name = regionDO.getTownName();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
