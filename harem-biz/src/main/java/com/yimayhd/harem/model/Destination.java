package com.yimayhd.harem.model;

import java.util.List;

import com.yimayhd.harem.model.TripBo.TripDetail;

public class Destination {
	public int id; /** id */
	public String cityName; /**目的地名称 */
	public String cityCode; /** */
	public int cityLevel; /** 级别 省市区 */
	public int[] tag; /** 标签 */
	public String logoURL; /** 封面图 */
	public String coverURL; /** 目的地图 */
	public List<TripDetail> TripDetail; /** 概况 */
	public int type;/** 1出发地，2目的地*/
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public String getCityCode() {
		return cityCode;
	}



	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}



	public int getCityLevel() {
		return cityLevel;
	}



	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}



	public String getLogoURL() {
		return logoURL;
	}



	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}



	public String getCoverURL() {
		return coverURL;
	}



	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}



	public List<TripDetail> getTripDetail() {
		return TripDetail;
	}



	public void setTripDetail(List<TripDetail> tripDetail) {
		TripDetail = tripDetail;
	}



	public int[] getTag() {
		return tag;
	}



	public void setTag(int[] tag) {
		this.tag = tag;
	}
	
	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}

}
