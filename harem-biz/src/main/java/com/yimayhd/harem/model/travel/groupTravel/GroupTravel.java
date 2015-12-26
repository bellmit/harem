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

import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
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
		Map<Integer, RouteItemDO> desMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> trafficMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> breakfastMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> lunchMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> dinnerMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> scenicMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> hotelMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> restaurantDetailMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> scenicDetailMap = new HashMap<Integer, RouteItemDO>();
		Map<Integer, RouteItemDO> hotelDetailMap = new HashMap<Integer, RouteItemDO>();
		List<RouteItemDO> routeItems = lineResult.getRouteItemDOList();
		if (CollectionUtils.isNotEmpty(routeItems)) {
			for (RouteItemDO routeItem : routeItems) {
				days.add(routeItem.getDay());
				if (routeItem.getType() == RouteItemBizType.DESCRIPTION.getType()) {
					desMap.put(routeItem.getDay(), routeItem);
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_TRAFFIC_INFO.getType()) {
					trafficMap.put(routeItem.getDay(), routeItem);
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DESC.getType()) {
					RouteItemDesc desc = routeItem.getRouteItemDesc();
					if (desc != null) {
						if (RouteItemType.BREAKFAST.name().equals(desc.getType())) {
							breakfastMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.LUNCH.name().equals(desc.getType())) {
							lunchMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.DINNER.name().equals(desc.getType())) {
							dinnerMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.SCENIC.name().equals(desc.getType())) {
							scenicMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.HOTEL.name().equals(desc.getType())) {
							hotelMap.put(routeItem.getDay(), routeItem);
						}
					}
				} else if (routeItem.getType() == RouteItemBizType.ROUTE_ITEM_DETAIL.getType()) {
					System.out.println(routeItem.getValue());
					RouteItemDetail detail = routeItem.getRouteItemDetail();
					if (detail != null) {
						if (RouteItemType.RESTAURANT.name().equals(detail.getType())) {
							restaurantDetailMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.SCENIC.name().equals(detail.getType())) {
							scenicDetailMap.put(routeItem.getDay(), routeItem);
						} else if (RouteItemType.HOTEL.name().equals(detail.getType())) {
							hotelDetailMap.put(routeItem.getDay(), routeItem);
						}
					}
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
					dinnerMap.get(day), scenicMap.get(day), hotelMap.get(day), restaurantDetailMap.get(day),
					scenicDetailMap.get(day), hotelDetailMap.get(day)));
		}
		this.tripInfo = tripDays;
	}

	public List<TripDay> getTripInfo() {
		return tripInfo;
	}

	public void setTripInfo(List<TripDay> tripInfo) {
		this.tripInfo = tripInfo;
	}

	@Override
	public void setRouteInfo(LinePublishDTO dto) {
		List<RouteItemDO> routeItemDOList = new ArrayList<RouteItemDO>();
		for (int i = 1; i <= this.tripInfo.size(); i++) {
			TripDay tripDay = this.tripInfo.get(i - 1);
			// 交通
			RouteItemDO routeItemTrafficInfo = tripDay.getRouteItemTrafficInfo(i);
			if (routeItemTrafficInfo != null) {
				routeItemDOList.add(routeItemTrafficInfo);
			}
			// 描述
			RouteItemDO routeItemDescription = tripDay.getRouteItemDescription(i);
			if (routeItemDescription != null) {
				routeItemDOList.add(routeItemDescription);
			}
			// 早餐
			RouteItemDO routeItemBreakfast = tripDay.getRouteItemBreakfast(i);
			if (routeItemBreakfast != null) {
				routeItemDOList.add(routeItemBreakfast);
			}
			// 午餐
			RouteItemDO routeItemLunch = tripDay.getRouteItemLunch(i);
			if (routeItemLunch != null) {
				routeItemDOList.add(routeItemLunch);
			}
			// 晚餐
			RouteItemDO routeItemDinner = tripDay.getRouteItemDinner(i);
			if (routeItemDinner != null) {
				routeItemDOList.add(routeItemDinner);
			}

			// 景区
			RouteItemDO routeItemScenic = tripDay.getRouteItemScenic(i);
			if (routeItemScenic != null) {
				routeItemDOList.add(routeItemScenic);
			}
			// 酒店
			RouteItemDO routeItemHotel = tripDay.getRouteItemHotel(i);
			if (routeItemHotel != null) {
				routeItemDOList.add(routeItemHotel);
			}
			// 餐厅详情
			RouteItemDO routeItemRestaurantDetail = tripDay.getRouteItemRestaurantDetail(i);
			if (routeItemRestaurantDetail != null) {
				routeItemDOList.add(routeItemRestaurantDetail);
			}
			// 景区详情
			RouteItemDO routeItemScenicDetail = tripDay.getRouteItemScenicDetail(i);
			if (routeItemScenicDetail != null) {
				routeItemDOList.add(routeItemScenicDetail);
			}
			// 酒店详情
			RouteItemDO routeItemHotelDetail = tripDay.getRouteItemHotelDetail(i);
			if (routeItemHotelDetail != null) {
				routeItemDOList.add(routeItemHotelDetail);
			}

		}
		dto.setRouteItemDOList(routeItemDOList);
		RouteDO routeDO = new RouteDO();
		routeDO.setPicture(this.baseInfo.getTripImage());
		dto.setRouteDO(routeDO);
	}

	@Override
	protected int getItemType() {
		return ItemType.LINE.getValue();
	}

}
