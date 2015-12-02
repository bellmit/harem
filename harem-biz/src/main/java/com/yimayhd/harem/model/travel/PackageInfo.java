package com.yimayhd.harem.model.travel;

import java.util.List;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
	private String startTime; // 开始时间
	private String endTime;// 结束时间
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

}
