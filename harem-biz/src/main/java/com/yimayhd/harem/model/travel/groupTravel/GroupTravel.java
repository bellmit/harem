package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
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
public class GroupTravel extends BaseTravel {
	private List<TripDay> tripInfo;// 行程信息

	@Override
	protected void parseTripInfo(LineResult lineResult) {
		List<TripDay> tripDays = new ArrayList<TripDay>();
		Set<Integer> days = new HashSet<Integer>();
		Map<Integer, String> desMap = new HashMap<Integer, String>();
		Map<Integer, RouteTrafficInfo> trafficMap = new HashMap<Integer, RouteTrafficInfo>();
		Map<Integer, RouteItemDesc> breakfastMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> lunchMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> dinnerMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> scenicMap = new HashMap<Integer, RouteItemDesc>();
		Map<Integer, RouteItemDesc> hotelMap = new HashMap<Integer, RouteItemDesc>();
		List<RouteItemDO> routeItems = lineResult.getRouteItemDOList();
		if (CollectionUtils.isNotEmpty(routeItems)) {
			for (RouteItemDO routeItem : routeItems) {
				days.add(routeItem.getDay());
				if (routeItem.getType() == RouteItemBizType.DESCRIPTION.getType()) {
					desMap.put(routeItem.getDay(), routeItem.getValue());
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_TRAFFIC_INFO.getType()) {
					RouteTrafficInfo routeTrafficInfo = JSON.parseObject(routeItem.getValue(), RouteTrafficInfo.class);
					trafficMap.put(routeItem.getDay(), routeTrafficInfo);
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DESC.getType()) {
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
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DETAIL.getType()) {
					// RouteItemDetail detail =
					// JSON.parseObject(routeItem.getValue(),
					// RouteItemDetail.class);
					// TODO 富文本
				}
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
		this.tripInfo = tripDays;
	}

	public List<TripDay> getTripInfo() {
		return tripInfo;
	}

	public void setTripInfo(List<TripDay> tripInfo) {
		this.tripInfo = tripInfo;
	}

}
