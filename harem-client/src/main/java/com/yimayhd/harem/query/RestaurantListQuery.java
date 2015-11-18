package com.yimayhd.harem.query;

import java.util.Date;

import com.yimayhd.harem.model.Region;

/**
 * 列表查询类
 * 
 * @author yebin
 *
 */
public class RestaurantListQuery implements Query {
	private String name;// 名称
	private Integer state;// 状态
	private String provinceCode;// 省
	private Region cityCode;// 市
	private String contact;// 联系人
	private Date createBeginDate;// 创建日期_开始时间
	private Date createEndDate;// 创建日期_结束时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Region getCityCode() {
		return cityCode;
	}

	public void setCityCode(Region cityCode) {
		this.cityCode = cityCode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreateBeginDate() {
		return createBeginDate;
	}

	public void setCreateBeginDate(Date createBeginDate) {
		this.createBeginDate = createBeginDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}
}
