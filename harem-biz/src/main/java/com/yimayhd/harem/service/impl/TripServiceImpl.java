package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.ShowcaseService;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.enums.ColumnType;
import com.yimayhd.resourcecenter.model.enums.RegionStatus;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class TripServiceImpl implements TripService {

	@Autowired RegionClientService regionClientServiceRef;
	
	@Autowired ShowcaseClientServer showcaseClientServerRef;
	
	@Autowired HotelService hotelService;
	
	@Autowired ScenicService scenicSpotService;
	
	@Autowired BoothClientServer boothClientServerRef;
	/*@Autowired ComCenterService ComCenterServiceRef;*/
	
	
	public long saveTrip(TripBo tripBo) {
		RcResult<RegionDO> res = regionClientServiceRef.selectById(tripBo.getId());
		if(null != res && res.isSuccess() && null != res.getT()){
			RegionDO regionDO = res.getT();
			regionDO.setGmtModified(new Date());
			regionDO.setStatus(RegionStatus.VALID.getStatus());
			regionClientServiceRef.updateById(regionDO);
			if(tripBo.getType()==4){//目的地
				//保存相应的概况  民俗等信息 
				List<NeedKnow> list = new ArrayList<NeedKnow>();
				NeedKnow gaikuang = tripBo.getGaikuang();
				gaikuang.setExtraInfoUrl(ColumnType.SURVER.toString());
				NeedKnow minsu = tripBo.getMinsu();
				minsu.setExtraInfoUrl(ColumnType.FOLKWAYS.toString());
				NeedKnow tieshi = tripBo.getTieshi();
				tieshi.setExtraInfoUrl(ColumnType.HIGHLIGHTS.toString());
				NeedKnow xiaofei = tripBo.getXiaofei();
				xiaofei.setExtraInfoUrl(ColumnType.CONSUMPTION.toString());
				list.add(gaikuang);
				list.add(minsu);
				list.add(tieshi);
				list.add(xiaofei);
				encaSaveShowCase(list, StringUtils.isEmpty(tripBo.getCityCode())?"test---111":tripBo.getCityCode());
			}
			return regionDO.getId();
		}
		return 0;
	}
	
	
	
	
	
	public void encaSaveShowCase(List<NeedKnow> list,String cityCode){
		//XXX:根据设计，流程如下：先往rc_booth表插城市的NeedKnow，然后根据返回的id,继续往rc_showcase表中插具体的NeedKnow包含的TextItem信息.
		for (int i = 1; i < list.size(); i++) {
			if(null != list.get(i)){
				NeedKnow nk = list.get(i);
				if(null != nk){
					BoothDO boothDO = new BoothDO();
					boothDO.setCode(nk.getExtraInfoUrl()+"_"+cityCode);
					boothDO.setName(ColumnType.getByName(nk.getExtraInfoUrl()).getCode());
					boothDO.setDesc(nk.getExtraInfoUrl()+"_"+cityCode);
					boothDO.setStatus(10);
					boothDO.setType(2);
					boothDO.setGmtCreated(new Date());
					RcResult<Long> res = boothClientServerRef.insert(boothDO);
					if(null !=res && res.isSuccess() && null != res.getT() ){
						boothDO.setId(res.getT());
						 RcResult<Boolean>  resb = showcaseClientServerRef.batchInsertShowcase(boothDO, needKnowToShowCase(nk, cityCode, res.getT()));
						 System.out.println("------"+resb);
					}
				}
				//System.out.println(i+" _ "+list.get(i).getTitle()+" _ "+list.get(i).getContent());
			}
		}
	}

	
	public List<ShowcaseDO> needKnowToShowCase(NeedKnow needKnow,String cityCode,long boothId){
		List<ShowcaseDO> listShowCase = new ArrayList<ShowcaseDO>();
		ShowcaseDO sw = null;
		List<TextItem> listItem = needKnow.getFrontNeedKnow();
		for (int i=0;i<listItem.size();i++) {
			if(null != listItem.get(i)){
				sw = new ShowcaseDO();
				sw.setGmtCreated(new Date());
				sw.setTitle(listItem.get(i).getTitle());
				sw.setBusinessCode(cityCode);
				sw.setBoothId(boothId);
				sw.setOperationContent(listItem.get(i).getContent());
				listShowCase.add(sw);
			}
		}
		return listShowCase;
		
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
		List<ShowCaseResult> list =null;
		return list;
	}
	
	
	@Override
	public List<HotelDO> selecthotelDO(HotelListQuery hotelListQuery) throws Exception {
		return hotelService.getList(hotelListQuery);
	}
	
	
}
