package com.yimayhd.harem.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.ShowcaseService;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionStatus;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.RegionClientService;

public class TripServiceImpl implements TripService {

	@Autowired RegionClientService regionClientServiceRef;
	
	@Autowired ShowcaseService showcaseService;
	
	@Autowired HotelService hotelService;
	
	@Autowired ScenicService scenicSpotService;
	
	
	public long saveTrip(TripBo tripBo) {
		RcResult<RegionDO> res = regionClientServiceRef.selectById(tripBo.getId());
		if(null != res && res.isSuccess()){
			RegionDO regionDO = res.getT();
			regionDO.setGmtModified(new Date());
			regionDO.setStatus(RegionStatus.VALID.getStatus());
			regionClientServiceRef.updateById(regionDO);
			return regionDO.getId();
		}
		return 0;
	}

	@Override
	public boolean delTrip(long id) {
		RcResult<Boolean> res = regionClientServiceRef.deleteById(id);
		if(null !=res && res.isSuccess()){
			return true;
		}
		return false;
	}

	@Override
	public RegionDO getTripBo(int id) {
		/*TripBo trip = new TripBo();
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
		trip.setZhiBo(new int[]{39,94,39});*/
		RcResult<RegionDO> res = regionClientServiceRef.selectById(id);
		if(null != res && res.isSuccess()){
			return res.getT();
		}
		return null;
	}

	@Override
	public List<RegionDO> getTripBo() {
		/*List<TripBo> list = new ArrayList<TripBo>();
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
		}*/
		
		return null;
	}

	@Override
	public boolean editTripBo(TripBo tripBo) {
		
		return false;
	}

	@Override
	public boolean relevanceRecommended(int type, int destinationId, int[] showCaseId) {
		
		return false;
	}

	@Override
	public List<RegionDO> selectRegion(int type) {
		RCPageResult<RegionDO> res = regionClientServiceRef.getRegionDOListByType(type);
		if(null !=res && CollectionUtils.isNotEmpty(res.getList())){
			return res.getList();
		}
		return null;
	}

	@Override
	public PageVO<ScenicDO> selectScenicDO(ScenicPageQuery scenicPageQuery) throws Exception {
		return scenicSpotService.getList(scenicPageQuery);
	}

	@Override
	public List<ShowCaseResult> getListShowCaseResult(int type) {
		List<ShowCaseResult> list = showcaseService.getListShowCaseResult(type);
		return list;
	}
	
	
	@Override
	public List<HotelDO> selecthotelDO(HotelListQuery hotelListQuery) throws Exception {
		return hotelService.getList(hotelListQuery);
	}
	
	
}
