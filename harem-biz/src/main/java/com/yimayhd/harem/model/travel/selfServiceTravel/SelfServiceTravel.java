package com.yimayhd.harem.model.travel.selfServiceTravel;

import com.yimayhd.harem.model.travel.BaseInfo;
import com.yimayhd.harem.model.travel.PriceInfo;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class SelfServiceTravel {

	private BaseInfo baseInfo;// 基础信息
	private PriceInfo priceInfo;// 价格信息

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

}
