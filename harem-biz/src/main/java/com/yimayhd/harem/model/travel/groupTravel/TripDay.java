package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDesc;
import com.yimayhd.ic.client.model.domain.share_json.RouteTextItem;
import com.yimayhd.ic.client.model.domain.share_json.RouteTrafficInfo;

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
	private List<IdNamePair> scenics;// 景区
	private List<IdNamePair> hotels;// 酒店

	public TripDay() {
	}

	public TripDay(RouteTrafficInfo trafficInfo, String description, RouteItemDesc breakfast, RouteItemDesc lunch,
			RouteItemDesc dinner, RouteItemDesc scenic, RouteItemDesc hotel) {
		this.traffic = new TripTraffic(trafficInfo);
		this.description = description;
		this.restaurant1 = new ArrayList<IdNamePair>();
		if (breakfast != null && breakfast.textItems != null) {
			for (RouteTextItem textItem : breakfast.textItems) {
				this.restaurant1.add(new IdNamePair(textItem.id, textItem.name));
			}
		}
		this.restaurant2 = new ArrayList<IdNamePair>();
		if (lunch != null && lunch.textItems != null) {
			for (RouteTextItem textItem : lunch.textItems) {
				this.restaurant2.add(new IdNamePair(textItem.id, textItem.name));
			}
		}
		this.restaurant3 = new ArrayList<IdNamePair>();
		if (dinner != null && dinner.textItems != null) {
			for (RouteTextItem textItem : dinner.textItems) {
				this.restaurant3.add(new IdNamePair(textItem.id, textItem.name));
			}
		}
		this.scenics = new ArrayList<IdNamePair>();
		if (scenic != null && scenic.textItems != null) {
			for (RouteTextItem textItem : scenic.textItems) {
				this.scenics.add(new IdNamePair(textItem.id, textItem.name));
			}
		}
		this.hotels = new ArrayList<IdNamePair>();
		if (hotel != null && hotel.textItems != null) {
			for (RouteTextItem textItem : hotel.textItems) {
				this.hotels.add(new IdNamePair(textItem.id, textItem.name));
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
}
