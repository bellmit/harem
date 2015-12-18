package com.yimayhd.harem.model.travel.groupTravel;

import java.util.List;

import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
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
	private List<RouteTextItem> restaurant1;// 早餐
	private List<RouteTextItem> restaurant2;// 午餐
	private List<RouteTextItem> restaurant3;// 晚餐
	private List<RouteTextItem> scenics;// 景区
	private List<RouteTextItem> hotels;// 酒店

	public TripDay() {
	}

	public TripDay(RouteTrafficInfo trafficInfo, String description, RouteItemDesc breakfast, RouteItemDesc lunch,
			RouteItemDesc dinner, RouteItemDesc scenic, RouteItemDesc hotel) {
		this.traffic = new TripTraffic(trafficInfo);
		this.description = description;
		this.restaurant1 = breakfast.textItems;
		this.restaurant2 = lunch.textItems;
		this.restaurant3 = dinner.textItems;
		this.scenics = scenic.textItems;
		this.hotels = hotel.textItems;
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
		routeItemDesc.textItems = this.restaurant1;
		routeItemDesc.type = RouteItemType.BREAKFAST.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemLunch() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		routeItemDesc.textItems = this.restaurant2;
		routeItemDesc.type = RouteItemType.LUNCH.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemDinner() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		routeItemDesc.textItems = this.restaurant3;
		routeItemDesc.type = RouteItemType.DINNER.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemScenic() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		routeItemDesc.textItems = this.scenics;
		routeItemDesc.type = RouteItemType.SCENIC.name();
		return routeItemDesc;
	}

	public RouteItemDesc getRouteItemHotel() {
		RouteItemDesc routeItemDesc = new RouteItemDesc();
		routeItemDesc.textItems = this.hotels;
		routeItemDesc.type = RouteItemType.HOTEL.name();
		return routeItemDesc;
	}

	public List<RouteTextItem> getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(List<RouteTextItem> restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public List<RouteTextItem> getRestaurant2() {
		return restaurant2;
	}

	public void setRestaurant2(List<RouteTextItem> restaurant2) {
		this.restaurant2 = restaurant2;
	}

	public List<RouteTextItem> getRestaurant3() {
		return restaurant3;
	}

	public void setRestaurant3(List<RouteTextItem> restaurant3) {
		this.restaurant3 = restaurant3;
	}

	public List<RouteTextItem> getScenics() {
		return scenics;
	}

	public void setScenics(List<RouteTextItem> scenics) {
		this.scenics = scenics;
	}

	public List<RouteTextItem> getHotels() {
		return hotels;
	}

	public void setHotels(List<RouteTextItem> hotels) {
		this.hotels = hotels;
	}
}
