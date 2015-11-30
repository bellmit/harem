package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.List;

import com.yimayhd.harem.model.travel.IdNameData;

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
	private List<IdNameData> hotels;// 酒店

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

	public List<IdNameData> getHotels() {
		return hotels;
	}

	public void setHotels(List<IdNameData> hotels) {
		this.hotels = hotels;
	}

	public int getHasFlight() {
		return hasFlight;
	}

	public void setHasFlight(int hasFlight) {
		this.hasFlight = hasFlight;
	}

}
