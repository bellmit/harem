package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.List;

import com.yimayhd.harem.model.travel.IdNamePair;

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
	private List<IdNamePair> hotels;// 酒店

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

	public int getHasFlight() {
		return hasFlight;
	}

	public void setHasFlight(int hasFlight) {
		this.hasFlight = hasFlight;
	}

	public List<IdNamePair> getHotels() {
		return hotels;
	}

	public void setHotels(List<IdNamePair> hotels) {
		this.hotels = hotels;
	}

}
