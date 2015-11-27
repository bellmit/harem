package com.yimayhd.harem.model.groupTravel;

import java.util.List;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
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

}
