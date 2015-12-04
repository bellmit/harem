package com.yimayhd.harem.model.travel;

import java.util.List;

import com.yimayhd.ic.client.model.domain.item.ItemDO;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfo {
	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天

	public PriceInfo() {
	}

	public PriceInfo(ItemDO item) {
		// TODO Auto-generated constructor stub
	}

	public List<PackageInfo> getTcs() {
		return tcs;
	}

	public void setTcs(List<PackageInfo> tcs) {
		this.tcs = tcs;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
