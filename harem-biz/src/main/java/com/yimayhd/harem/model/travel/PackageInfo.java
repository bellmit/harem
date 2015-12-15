package com.yimayhd.harem.model.travel;

import java.util.List;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
	private long type; // 套餐值类型
	private String name;// 套餐名
	private List<PackageMonth> months;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PackageMonth> getMonths() {
		return months;
	}

	public void setMonths(List<PackageMonth> months) {
		this.months = months;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}
}
