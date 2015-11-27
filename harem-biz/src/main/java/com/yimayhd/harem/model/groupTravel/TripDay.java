package com.yimayhd.harem.model.groupTravel;

import java.util.List;
import java.util.Map;

/**
 * 行程-天
 * 
 * @author yebin
 *
 */
public class TripDay {
	private long id;// ID
	private Map<String, String> traffic;// 交通方式
	private String description;// 描述
	private Map<String, String> restaurant1;// 早餐
	private Map<String, String> restaurant2;// 午餐
	private Map<String, String> restaurant3;// 晚餐
	private List<Map<String, String>> scenics;// 景区
	private List<Map<String, String>> hotels;// 酒店

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<String, String> getTraffic() {
		return traffic;
	}

	public void setTraffic(Map<String, String> traffic) {
		this.traffic = traffic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getRestaurant1() {
		return restaurant1;
	}

	public void setRestaurant1(Map<String, String> restaurant1) {
		this.restaurant1 = restaurant1;
	}

	public Map<String, String> getRestaurant2() {
		return restaurant2;
	}

	public void setRestaurant2(Map<String, String> restaurant2) {
		this.restaurant2 = restaurant2;
	}

	public Map<String, String> getRestaurant3() {
		return restaurant3;
	}

	public void setRestaurant3(Map<String, String> restaurant3) {
		this.restaurant3 = restaurant3;
	}

	public List<Map<String, String>> getScenics() {
		return scenics;
	}

	public void setScenics(List<Map<String, String>> scenics) {
		this.scenics = scenics;
	}

	public List<Map<String, String>> getHotels() {
		return hotels;
	}

	public void setHotels(List<Map<String, String>> hotels) {
		this.hotels = hotels;
	}

}
