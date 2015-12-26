package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.domain.share_json.RouteTextItem;
import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;
import com.yimayhd.ic.client.model.enums.RouteItemBizType;
import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 行程-天
 * 
 * @author yebin
 *
 */
public class TripDay {
	private TripTraffic traffic;// 交通方式
	private String description;// 描述
	private List<IdNamePair> restaurant1;// 早餐
	private List<IdNamePair> restaurant2;// 午餐
	private List<IdNamePair> restaurant3;// 晚餐
	private RouteItemDetail restaurantDetail;
	private List<IdNamePair> scenics;// 景区
	private RouteItemDetail scenicDetail;
	private List<IdNamePair> hotels;// 酒店
	private RouteItemDetail hotelDetail;

	public TripDay() {
	}

	public TripDay(RouteTrafficInfo trafficInfo, String description, RouteItemDesc breakfast, RouteItemDesc lunch,
			RouteItemDesc dinner, RouteItemDesc scenic, RouteItemDesc hotel) {
		this.traffic = new TripTraffic(trafficInfo);
		this.description = description;
		this.restaurant1 = new ArrayList<IdNamePair>();
		if (breakfast != null && CollectionUtils.isNotEmpty(breakfast.getTextItems())) {
			for (RouteTextItem routeTextItem : breakfast.getTextItems()) {
				this.restaurant1.add(new IdNamePair(routeTextItem));
			}
		}
		this.restaurant2 = new ArrayList<IdNamePair>();
		if (lunch != null && CollectionUtils.isNotEmpty(lunch.getTextItems())) {
			for (RouteTextItem routeTextItem : lunch.getTextItems()) {
				this.restaurant2.add(new IdNamePair(routeTextItem));
			}
		}
		this.restaurant3 = new ArrayList<IdNamePair>();
		if (dinner != null && CollectionUtils.isNotEmpty(dinner.getTextItems())) {
			for (RouteTextItem routeTextItem : dinner.getTextItems()) {
				this.restaurant3.add(new IdNamePair(routeTextItem));
			}
		}
		this.scenics = new ArrayList<IdNamePair>();
		if (scenic != null && CollectionUtils.isNotEmpty(scenic.getTextItems())) {
			for (RouteTextItem routeTextItem : scenic.getTextItems()) {
				this.scenics.add(new IdNamePair(routeTextItem));
			}
		}
		this.hotels = new ArrayList<IdNamePair>();
		if (hotel != null && CollectionUtils.isNotEmpty(hotel.getTextItems())) {
			for (RouteTextItem routeTextItem : hotel.getTextItems()) {
				this.hotels.add(new IdNamePair(routeTextItem));
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TripTraffic getTraffic() {
		return traffic;
	}

	public void setTraffic(TripTraffic traffic) {
		this.traffic = traffic;
	}

	public RouteItemDO getRouteItemTrafficInfo(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_TRAFFIC_INFO.getType());
		routeItemDO.setValue(JSON.toJSONString(this.traffic.toRouteTrafficInfo()));
		return routeItemDO;
	}

	public RouteItemDO getRouteItemDescription(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.DESCRIPTION.getType());
		routeItemDO.setValue(this.getDescription());
		return routeItemDO;
	}

	public RouteItemDO getRouteItemBreakfast(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant1)) {
			for (IdNamePair pair : this.restaurant1) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.BREAKFAST.name());
				items.add(item);
			}
		}
		routeItemDesc.setTextItems(items);
		routeItemDesc.setType(RouteItemType.BREAKFAST.name());
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
		routeItemDO.setValue(JSON.toJSONString(routeItemDesc));
		return routeItemDO;
	}

	public RouteItemDO getRouteItemLunch(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant2)) {
			for (IdNamePair pair : this.restaurant2) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.LUNCH.name());
				items.add(item);
			}
		}
		routeItemDesc.setTextItems(items);
		routeItemDesc.setType(RouteItemType.LUNCH.name());
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
		routeItemDO.setValue(JSON.toJSONString(routeItemDesc));
		return routeItemDO;
	}

	public RouteItemDO getRouteItemDinner(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant3)) {
			for (IdNamePair pair : this.restaurant3) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.DINNER.name());
				items.add(item);
			}
		}
		routeItemDesc.setTextItems(items);
		routeItemDesc.setType(RouteItemType.DINNER.name());
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
		routeItemDO.setValue(JSON.toJSONString(routeItemDesc));
		return routeItemDO;
	}

	public RouteItemDO getRouteItemScenic(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.scenics)) {
			for (IdNamePair pair : this.scenics) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.SCENIC.name());
				items.add(item);
			}
		}
		routeItemDesc.setTextItems(items);
		routeItemDesc.setType(RouteItemType.SCENIC.name());
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
		routeItemDO.setValue(JSON.toJSONString(routeItemDesc));
		return routeItemDO;
	}

	public RouteItemDO getRouteItemHotel(int day) {
		RouteItemDO routeItemDO = new RouteItemDO();
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.hotels)) {
			for (IdNamePair pair : this.hotels) {
				RouteTextItem item = new RouteTextItem();
				item.setId(pair.getId());
				item.setName(pair.getName());
				item.setType(RouteItemType.HOTEL.name());
				items.add(item);
			}
		}
		routeItemDesc.setTextItems(items);
		routeItemDesc.setType(RouteItemType.HOTEL.name());
		routeItemDO.setDay(day);
		routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
		routeItemDO.setValue(JSON.toJSONString(routeItemDesc));
		return routeItemDO;
	}

	public List<IdNamePair> getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(List<IdNamePair> restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public List<IdNamePair> getRestaurant2() {
		return restaurant2;
	}

	public void setRestaurant2(List<IdNamePair> restaurant2) {
		this.restaurant2 = restaurant2;
	}

	public List<IdNamePair> getRestaurant3() {
		return restaurant3;
	}

	public void setRestaurant3(List<IdNamePair> restaurant3) {
		this.restaurant3 = restaurant3;
	}

	public List<IdNamePair> getScenics() {
		return scenics;
	}

	public void setScenics(List<IdNamePair> scenics) {
		this.scenics = scenics;
	}

	public List<IdNamePair> getHotels() {
		return hotels;
	}

	public void setHotels(List<IdNamePair> hotels) {
		this.hotels = hotels;
	}

	public long getRestaurantDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(restaurant1)) {
			for (IdNamePair idNamePair : restaurant1) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		if (id > 0 && CollectionUtils.isNotEmpty(restaurant2)) {
			for (IdNamePair idNamePair : restaurant2) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		if (id > 0 && CollectionUtils.isNotEmpty(restaurant3)) {
			for (IdNamePair idNamePair : restaurant3) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public long getScenicDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(scenics)) {
			for (IdNamePair idNamePair : scenics) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public long getHotelDetailId() {
		long id = 0;
		if (CollectionUtils.isNotEmpty(hotels)) {
			for (IdNamePair idNamePair : hotels) {
				if (idNamePair.getId() > 0) {
					id = idNamePair.getId();
					break;
				}
			}
		}
		return id;
	}

	public RouteItemDO getRouteItemRestaurantDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.restaurantDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DETAIL.getType());
			routeItemDO.setValue(JSON.toJSONString(this.description));
		}
		return routeItemDO;
	}

	public void setRestaurantDetail(RouteItemDetail restaurantDetail) {
		this.restaurantDetail = restaurantDetail;
	}

	public RouteItemDO getRouteItemScenicDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.scenicDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setValue(JSON.toJSONString(this.scenicDetail));
		}
		return routeItemDO;
	}

	public void setScenicDetail(RouteItemDetail scenicDetail) {
		this.scenicDetail = scenicDetail;
	}

	public RouteItemDO getRouteItemHotelDetail(int day) {
		RouteItemDO routeItemDO = null;
		if (this.hotelDetail != null) {
			routeItemDO = new RouteItemDO();
			routeItemDO.setDay(day);
			routeItemDO.setType(RouteItemBizType.ROUTE_ITEM_DESC.getType());
			routeItemDO.setValue(JSON.toJSONString(this.hotelDetail));
		}
		return routeItemDO;
	}

	public void setHotelDetail(RouteItemDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}
}
