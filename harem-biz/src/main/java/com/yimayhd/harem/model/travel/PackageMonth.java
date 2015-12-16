package com.yimayhd.harem.model.travel;

import java.util.Date;
import java.util.List;

/**
 * 套餐-月
 * 
 * @author yebin
 *
 */
public class PackageMonth {
	private Date date;// 日期
	private List<PackageDay> days;// 日期列表

	public PackageMonth() {
	}

	public PackageMonth(long date) {
		this.date = new Date(date);
	}

	public List<PackageDay> getDays() {
		return days;
	}

	public void setDays(List<PackageDay> days) {
		this.days = days;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
