package com.yimayhd.harem.model.travel;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public abstract class BaseTravel {
	protected BaseInfo baseInfo;// 基础信息
	protected PriceInfo priceInfo;// 价格信息

	public void init(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		// TODO YEBIN DO对象解析
		LineDO line = lineResult.getLineDO();
		if (line != null) {
			this.baseInfo = new BaseInfo(line, comTagDOs);
		}
		RouteDO route = lineResult.getRouteDO();
		if (route != null && this.baseInfo != null) {
			this.baseInfo.setTripImage(route.getPicture());
		}
		parseTripInfo(lineResult);
		this.priceInfo = new PriceInfo(lineResult);
	}

	protected abstract void parseTripInfo(LineResult lineResult);

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
