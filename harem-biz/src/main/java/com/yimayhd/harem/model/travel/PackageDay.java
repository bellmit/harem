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
	private List<PackageBlock> blocks;// 内容块

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PackageBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<PackageBlock> blocks) {
		this.blocks = blocks;
	}

}
