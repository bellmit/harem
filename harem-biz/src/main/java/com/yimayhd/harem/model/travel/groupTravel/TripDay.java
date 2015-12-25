package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.domain.share_json.RouteTextItem;
import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;
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
		if (breakfast != null && CollectionUtils.isNotEmpty(breakfast.textItems)) {
			for (RouteTextItem routeTextItem : breakfast.textItems) {
				this.restaurant1.add(new IdNamePair(routeTextItem));
			}
		}
		this.restaurant2 = new ArrayList<IdNamePair>();
		if (lunch != null && CollectionUtils.isNotEmpty(lunch.textItems)) {
			for (RouteTextItem routeTextItem : lunch.textItems) {
				this.restaurant2.add(new IdNamePair(routeTextItem));
			}
		}
		this.restaurant3 = new ArrayList<IdNamePair>();
		if (dinner != null && CollectionUtils.isNotEmpty(dinner.textItems)) {
			for (RouteTextItem routeTextItem : dinner.textItems) {
				this.restaurant3.add(new IdNamePair(routeTextItem));
			}
		}
		this.scenics = new ArrayList<IdNamePair>();
		if (scenic != null && CollectionUtils.isNotEmpty(scenic.textItems)) {
			for (RouteTextItem routeTextItem : scenic.textItems) {
				this.scenics.add(new IdNamePair(routeTextItem));
			}
		}
		this.hotels = new ArrayList<IdNamePair>();
		if (hotel != null && CollectionUtils.isNotEmpty(hotel.textItems)) {
			for (RouteTextItem routeTextItem : hotel.textItems) {
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

	public RouteTrafficInfo getRouteTrafficInfo() {
		return this.traffic.toRouteTrafficInfo();
	}

	public RouteItemDesc getRouteItemBreakfast() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant1)) {
			for (IdNamePair pair : this.restaurant1) {
				RouteTextItem item = new RouteTextItem();
				item.id = pair.getId();
				item.name = pair.getName();
				item.type = RouteItemType.BREAKFAST.name();
				items.add(item);
			}
		}
		routeItemDesc.textItems = items;
		routeItemDesc.type = RouteItemType.BREAKFAST.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemLunch() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant2)) {
			for (IdNamePair pair : this.restaurant2) {
				RouteTextItem item = new RouteTextItem();
				item.id = pair.getId();
				item.name = pair.getName();
				item.type = RouteItemType.LUNCH.name();
				items.add(item);
			}
		}
		routeItemDesc.textItems = items;
		routeItemDesc.type = RouteItemType.LUNCH.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemDinner() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.restaurant3)) {
			for (IdNamePair pair : this.restaurant3) {
				RouteTextItem item = new RouteTextItem();
				item.id = pair.getId();
				item.name = pair.getName();
				item.type = RouteItemType.DINNER.name();
				items.add(item);
			}
		}
		routeItemDesc.textItems = items;
		routeItemDesc.type = RouteItemType.DINNER.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemScenic() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.scenics)) {
			for (IdNamePair pair : this.scenics) {
				RouteTextItem item = new RouteTextItem();
				item.id = pair.getId();
				item.name = pair.getName();
				item.type = RouteItemType.SCENIC.name();
				items.add(item);
			}
		}
		routeItemDesc.textItems = items;
		routeItemDesc.type = RouteItemType.SCENIC.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemHotel() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		List<RouteTextItem> items = new ArrayList<RouteTextItem>();
		if (CollectionUtils.isNotEmpty(this.hotels)) {
			for (IdNamePair pair : this.hotels) {
				RouteTextItem item = new RouteTextItem();
				item.id = pair.getId();
				item.name = pair.getName();
				item.type = RouteItemType.HOTEL.name();
				items.add(item);
			}
		}
		routeItemDesc.textItems = items;
		routeItemDesc.type = RouteItemType.HOTEL.name();
		return routeItemDesc;
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

	public RouteItemDetail getRestaurantDetail() {
		return restaurantDetail;
	}

	public void setRestaurantDetail(RouteItemDetail restaurantDetail) {
		this.restaurantDetail = restaurantDetail;
	}

	public RouteItemDetail getScenicDetail() {
		return scenicDetail;
	}

	public void setScenicDetail(RouteItemDetail scenicDetail) {
		this.scenicDetail = scenicDetail;
	}

	public RouteItemDetail getHotelDetail() {
		return hotelDetail;
	}

	public void setHotelDetail(RouteItemDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}
}
