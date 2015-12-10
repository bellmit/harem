package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.TripBo.TripDetail;
import com.yimayhd.harem.service.TripService;

public class TripServiceImpl implements TripService {

	@Override
	public int saveTrip(TripBo tripBo) {
		
		return 0;
	}

	@Override
	public boolean delTrip(TripBo tripBo) {
		
		return false;
	}

	@Override
	public TripBo getTripBo(int id) {
		TripBo trip = new TripBo();
		trip.setBiMai(new int[]{1,2,3});
		trip.setBiQu(new int[]{3,4,5});
		trip.setCityCode("asdda111");
		trip.setCityLevel(1);
		trip.setCityName("大声道");
		trip.setCoverURL("http://www.baidu.com");
		trip.setId(1);
		trip.setJiuDian(new int[]{5,6,7});
		trip.setLogoURL("http://www.baidu.com");
		trip.setTag(new int[]{7});
		List<TripBo.TripDetail> s = new ArrayList<TripDetail>();
		trip.setTripDetail(s);
		trip.setType(1);
		trip.setXianLu(new int[]{19,19,29});
		trip.setZhiBo(new int[]{39,94,39});
		return trip;
	}

	@Override
	public List<TripBo> getTripBo() {
		List<TripBo> list = new ArrayList<TripBo>();
		for (int i=0;i<100;i++) {
			TripBo trip = new TripBo();
			trip.setBiMai(new int[]{1,2,3});
			trip.setBiQu(new int[]{3,4,5});
			trip.setCityCode("asdda111"+i);
			trip.setCityLevel(1);
			trip.setCityName("大声道"+i);
			trip.setCoverURL("http://www.baidu.com");
			trip.setId(1);
			trip.setJiuDian(new int[]{5,6,7});
			trip.setLogoURL("http://www.baidu.com");
			trip.setTag(new int[]{7});
			List<TripBo.TripDetail> s = new ArrayList<TripDetail>();
			trip.setTripDetail(s);
			trip.setType(1);
			trip.setXianLu(new int[]{19,19,29});
			trip.setZhiBo(new int[]{39,94,39});
			list.add(trip);
		}
		return list;
	}

	@Override
	public boolean editTripBo(TripBo tripBo) {
		
		return false;
	}

	@Override
	public boolean relevanceRecommended(int type, int destinationId, int[] showCaseId) {
		
		return false;
	}

}
