package com.yimayhd.harem.model.travel;

import java.util.Date;

/**
 * 套餐-日
 * 
 * @author yebin
 *
 */
public class PackageDay {
	private Date date;// 日期
	private PackageBlock adult;// 成人
	private PackageBlock children02;// 儿童0-2
	private PackageBlock children212;// 儿童2-12
	private String srd;// 单房差

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PackageBlock getAdult() {
		return adult;
	}

	public void setAdult(PackageBlock adult) {
		this.adult = adult;
	}

	public PackageBlock getChildren02() {
		return children02;
	}

	public void setChildren02(PackageBlock children02) {
		this.children02 = children02;
	}

	public PackageBlock getChildren212() {
		return children212;
	}

	public void setChildren212(PackageBlock children212) {
		this.children212 = children212;
	}

	public String getSrd() {
		return srd;
	}

	public void setSrd(String srd) {
		this.srd = srd;
	}

}
