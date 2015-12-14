package com.yimayhd.harem.model.travel;

import java.util.Date;
import java.util.List;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
	private Date startDate; // 开始时间
	private Date endDate;// 结束时间
	private long type; // 套餐值类型
	private String name;// 套餐名
	private String from;// 出发地
	private List<PackageMonth> months;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<PackageMonth> getMonths() {
		return months;
	}

	public void setMonths(List<PackageMonth> months) {
		this.months = months;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}
}
