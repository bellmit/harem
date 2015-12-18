package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.share_json.FlightDetail;
import com.yimayhd.ic.client.model.domain.share_json.FlightInfo;
import com.yimayhd.ic.client.model.domain.share_json.HotelInfo;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 行程套餐信息
 * 
 * @author yebin
 *
 */
public class TripPackageInfo {
	private int hasFlight;// 有无航班
	private FlightInfoVO flightInfo;// 航班信息
	private List<FlightVO> flights;// 航班列表
	private List<HotelInfo> hotels;// 酒店

	public TripPackageInfo() {
	}

	public TripPackageInfo(LineResult lineResult) {
		LineDO line = lineResult.getLineDO();
		// 有无航班
		this.hasFlight = StringUtils.isNotBlank(line.getFlightDetail()) ? 1 : 0;
		// 航班信息
		if (StringUtils.isNotBlank(line.getFlights())) {
			FlightInfo flightInfo = JSON.parseObject(line.getFlights(), FlightInfo.class);
			this.flightInfo = new FlightInfoVO(flightInfo);
		}
		// 航班列表
		this.flights = new ArrayList<FlightVO>();
		if (StringUtils.isNotBlank(line.getFlightDetail())) {
			List<FlightDetail> fligthDetails = JSON.parseArray(line.getFlightDetail(), FlightDetail.class);
			for (FlightDetail flightDetail : fligthDetails) {
				this.flights.add(new FlightVO(flightDetail));
			}
		}
		// 酒店
		this.hotels = JSON.parseArray(line.getHotels(), HotelInfo.class);
	}

	public int getHasFlight() {
		return hasFlight;
	}

	public void setHasFlight(int hasFlight) {
		this.hasFlight = hasFlight;
	}

	public List<HotelInfo> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelInfo> hotels) {
		this.hotels = hotels;
	}

	public FlightInfoVO getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(FlightInfoVO flightInfo) {
		this.flightInfo = flightInfo;
	}

	public List<FlightVO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightVO> flights) {
		this.flights = flights;
	}

}
