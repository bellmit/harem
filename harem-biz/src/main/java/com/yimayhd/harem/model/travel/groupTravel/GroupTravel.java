package com.yimayhd.harem.model.travel.groupTravel;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.harem.model.travel.BaseInfo;
import com.yimayhd.harem.model.travel.PriceInfo;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class GroupTravel {
	private BaseInfo baseInfo;// 基础信息
	private List<TripDay> tripInfo;// 行程信息
	private PriceInfo priceInfo;// 价格信息

	public GroupTravel() {
	}

	public GroupTravel(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		// TODO YEBIN DO对象解析
		LineDO line = lineResult.getLineDO();
		RouteDO route = lineResult.getRouteDO();
		this.baseInfo = new BaseInfo(line, route.getPicture(), comTagDOs);
		this.tripInfo = parseTripInfo(route);
		// priceInfo
	}

	private static List<TripDay> parseTripInfo(RouteDO route) {
		return null;
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

	public List<TripDay> getTripInfo() {
		return tripInfo;
	}

	public void setTripInfo(List<TripDay> tripInfo) {
		this.tripInfo = tripInfo;
	}

	/**
	 * 生成数据库对象
	 * 
	 * @return
	 */
	public LineDO toLineDO() {
		// TODO YEBIN 生产DO对象
		LineDO line = new LineDO();
		// baseInfo
		line.setName(baseInfo.getName());
		line.setCoverUrl("");
		line.setLogoUrl("");
		// tag =>关联记录
		line.setDestProvinceId(1L);
		line.setDestCityId(1L);
		line.setDestCityName("");
		// 设计亮点
		line.setDescription("");
		// 代言
		line.setRecommend("");
		// 报名须知+富文本
		line.setNeedKnow("");
		// tripInfo
		line.setScenics("");
		line.setHotels("");
		return null;
	}

}
