package com.yimayhd.harem.model.travel.groupTravel;

import java.util.List;

import com.yimayhd.harem.model.travel.BaseInfo;
import com.yimayhd.harem.model.travel.PriceInfo;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class GroupTravel {
	private long id;
	private BaseInfo baseInfo;// 基础信息
	private List<TripDay> tripInfo;// 行程信息
	private PriceInfo priceInfo;// 价格信息

	public List<TripDay> getTripInfo() {
		return tripInfo;
	}

	public void setTripInfo(List<TripDay> tripInfo) {
		this.tripInfo = tripInfo;
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
