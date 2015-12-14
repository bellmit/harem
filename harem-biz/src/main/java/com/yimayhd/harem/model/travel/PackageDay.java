package com.yimayhd.harem.model.travel;

import java.util.Date;
import java.util.List;

/**
 * 套餐-日
 * 
 * @author yebin
 *
 */
public class PackageDay {
	private Date date;// 日期
	private List<PackageBlock> packageBlocks;// 内容块

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PackageBlock> getPackageBlocks() {
		return packageBlocks;
	}

	public void setPackageBlocks(List<PackageBlock> packageBlocks) {
		this.packageBlocks = packageBlocks;
	}

}
