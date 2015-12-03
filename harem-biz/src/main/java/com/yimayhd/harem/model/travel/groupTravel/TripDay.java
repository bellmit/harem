package com.yimayhd.harem.model.travel.groupTravel;

import java.util.List;

import com.yimayhd.harem.model.travel.IdNamePair;

/**
 * 行程-天
 * 
 * @author yebin
 *
 */
public class TripDay {
	private long id;// ID
	private TripTraffic traffic;// 交通方式
	private String description;// 描述
	private List<IdNamePair> restaurant1;// 早餐
	private List<IdNamePair> restaurant2;// 午餐
	private List<IdNamePair> restaurant3;// 晚餐
	private List<IdNamePair> scenics;// 景区
	private List<IdNamePair> hotels;// 酒店

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
