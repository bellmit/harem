package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.List;
import java.util.Map;

/**
 * 行程套餐信息
 * 
 * @author yebin
 *
 */
public class TripPackageInfo {
	private int hasFlight;// 有无航班
	private String noteInfo;// 备注
	private List<FlightInfo> flights;// 航班信息
	private List<Map<String, String>> hotels;// 酒店

	public String getNoteInfo() {
		return noteInfo;
	}

	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}

	public List<FlightInfo> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightInfo> flights) {
		this.flights = flights;
	}

	public List<Map<String, String>> getHotels() {
		return hotels;
	}

	public void setHotels(List<Map<String, String>> hotels) {
		this.hotels = hotels;
	}

	public int getHasFlight() {
		return hasFlight;
	}

	public void setHasFlight(int hasFlight) {
		this.hasFlight = hasFlight;
	}

}
