package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.harem.model.travel.BaseInfo;
import com.yimayhd.harem.model.travel.PriceInfo;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
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
		if (line != null) {
			this.baseInfo = new BaseInfo(line, comTagDOs);
		}
		RouteDO route = lineResult.getRouteDO();
		if (route != null && this.baseInfo != null) {
			this.baseInfo.setTripImage(route.getPicture());
		}
		List<RouteItemDO> routeItems = lineResult.getRouteItemDOList();
		if (routeItems != null) {
			this.tripInfo = parseTripInfo(routeItems);
		}
		ItemDO item = lineResult.getItemDO();
		if (item != null) {
			this.priceInfo = new PriceInfo(item);
		}
	}

	/**
	 * 解析route
	 * 
	 * @param route
	 * @return
	 */
	private static List<TripDay> parseTripInfo(List<RouteItemDO> routeItems) {
		List<TripDay> tripDays = new ArrayList<TripDay>();
		Set<Integer> days = new HashSet<Integer>();
		Map<Integer, String> desMap = new HashMap<Integer, String>();
		Map<Integer, RouteTrafficInfo> trafficMap = new HashMap<Integer, RouteTrafficInfo>();
		Map<Integer, RouteItemDesc> breakfastMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> lunchMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> dinnerMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> scenicMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> hotelMap = new HashMap<Integer, RouteItemDesc>();
		for (RouteItemDO routeItem : routeItems) {
			days.add(routeItem.getDay());
			if (routeItem.getType() == RouteItemBizType.DESCRIPTION.getType()) {
				desMap.put(routeItem.getDay(), routeItem.getValue());
			} else if (routeItem.getType() == RouteItemBizType.TRAFFIC.getType()) {
				RouteTrafficInfo routeTrafficInfo = JSON.parseObject(routeItem.getValue(), RouteTrafficInfo.class);
				trafficMap.put(routeItem.getDay(), routeTrafficInfo);
			} else if (routeItem.getType() == RouteItemBizType.SHORT_ITEM.getType()) {
				RouteItemDesc desc = JSON.parseObject(routeItem.getValue(), RouteItemDesc.class);
				if (desc != null) {
					if (RouteItemType.BREAKFAST.name().equals(desc.type)) {
						breakfastMap.put(routeItem.getDay(), desc);
					} else if (RouteItemType.LUNCH.name().equals(desc.type)) {
						lunchMap.put(routeItem.getDay(), desc);
					} else if (RouteItemType.DINNER.name().equals(desc.type)) {
						dinnerMap.put(routeItem.getDay(), desc);
					} else if (RouteItemType.SCENIC.name().equals(desc.type)) {
						scenicMap.put(routeItem.getDay(), desc);
					} else if (RouteItemType.HOTEL.name().equals(desc.type)) {
						hotelMap.put(routeItem.getDay(), desc);
					}
				}
			} else if (routeItem.getType() == RouteItemBizType.DETAIL.getType()) {
				// RouteItemDetail detail = JSON.parseObject(routeItem.getValue(), RouteItemDetail.class);
				// TODO 富文本
			}
		}
		List<Integer> dayList = new ArrayList<Integer>(days);
		Collections.sort(dayList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		for (Integer day : dayList) {
			tripDays.add(new TripDay(trafficMap.get(day), desMap.get(day), breakfastMap.get(day), lunchMap.get(day),
					dinnerMap.get(day), scenicMap.get(day), hotelMap.get(day)));
		}
		return tripDays;
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
