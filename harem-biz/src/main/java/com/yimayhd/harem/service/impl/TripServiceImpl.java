package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.enums.ColumnType;
import com.yimayhd.resourcecenter.model.enums.RegionStatus;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class TripServiceImpl implements TripService {

	@Autowired RegionClientService regionClientServiceRef;
	
	@Autowired ShowcaseClientServer showcaseClientServerRef;
	
	@Autowired HotelService hotelService;
	
	@Autowired ScenicService scenicSpotService;
	
	@Autowired BoothClientServer boothClientServerRef;
	
	@Autowired ItemQueryService itemQueryServiceRef;
	
	@Autowired RegionIntroduceClientService RegionIntroduceClientServiceRef;	
	
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
				encaSaveShowCase(list,tripBo.getCityCode());
			}
			return regionDO.getId();
		}
		return 11;
	}
	
	public void encaSaveShowCase(List<NeedKnow> list,String cityCode){
		//XXX:根据设计，流程如下：先往rc_booth表插城市的NeedKnow，然后根据返回的id,继续往rc_showcase表中插具体的NeedKnow包含的TextItem信息.
		for (NeedKnow nk : list) {
			if(null != nk && CollectionUtils.isNotEmpty(nk.getFrontNeedKnow())){
				System.out.println(nk.getExtraInfoUrl());
				BoothDO boothDO = new BoothDO();
				boothDO.setCode(nk.getExtraInfoUrl()+"-"+cityCode);
				boothDO.setName(ColumnType.getByName(nk.getExtraInfoUrl()).getCode());
				boothDO.setDesc(nk.getExtraInfoUrl()+"-"+cityCode);
				boothDO.setStatus(10);
				boothDO.setType(2);
				boothDO.setGmtCreated(new Date());
				boothDO.setGmtModified(new Date());
				List<ShowcaseDO> listShowcaseDO = needKnowToShowCase(nk, cityCode,0);
				RcResult<Boolean>  resb = showcaseClientServerRef.batchInsertShowcase(listShowcaseDO,boothDO);
				System.out.println(resb.isSuccess());
			}
		}
	}

	/**
	* @Title: needKnowToShowCase 
	* @Description:(将needKnow转换成showcase) 
	* @author create by yushengwei @ 2015年12月19日 下午3:05:35 
	* @param @param needKnow
	* @param @param cityCode
	* @param @param boothId
	* @param @return 
	* @return List<ShowcaseDO> 返回类型 
	* @throws
	 */
	public List<ShowcaseDO> needKnowToShowCase(NeedKnow needKnow,String cityCode,long boothId){
		List<ShowcaseDO> listShowCase = new ArrayList<ShowcaseDO>();
		ShowcaseDO sw = null;
		List<TextItem> listItem = needKnow.getFrontNeedKnow();
		for (int i=0;i<listItem.size();i++) {
			if(null != listItem.get(i) && StringUtils.isNotEmpty(listItem.get(i).getTitle())){
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
	public boolean relevanceRecommended(int type, String cityCode, int[] resourceId)throws Exception {
		ColumnType columnType =  ColumnType.getByType(type);
		if(null == columnType ){
			throw new Exception("parameter[type] "+type+" ,Enum does not exist");
		}
		BoothDO boothDO = new BoothDO();
		boothDO.setCode(columnType.toString()+"-"+cityCode);
		boothDO.setName(columnType.getCode());
		boothDO.setDesc(columnType.getCode()+"-"+cityCode);
		boothDO.setStatus(10);
		boothDO.setType(2);
		boothDO.setGmtCreated(new Date());
		boothDO.setGmtModified(new Date());
		List<ShowcaseDO> list = new ArrayList<ShowcaseDO>();
		ShowcaseDO sc = null;
		for (int i=0;i<resourceId.length;i++) {
			sc= new ShowcaseDO();
			sc.setTitle("目的地_"+cityCode+"	关联	"+columnType.getCode()+" ["+resourceId[i]+"]");
			sc.setStatus(10);//BoothStatusType.ON_SHELF.getValue()
			sc.setGmtCreated(new Date());
			sc.setGmtModified(new Date());
			sc.setOperationContent(String.valueOf(resourceId[i]));
			list.add(sc);
		}
		RcResult<Boolean>  resb = showcaseClientServerRef.batchInsertShowcase(list,boothDO);
		System.out.println(resb.isSuccess());
		return resb.isSuccess();
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
	public List<RegionIntroduceDO> getListShowCaseResult(int type) {
		RegionIntroduceQuery regionIntroduceQuery = new RegionIntroduceQuery();
		regionIntroduceQuery.setType(type);
//		List<RegionIntroduceDO> list = RegionIntroduceClientServiceRef.queryRegionIntroduceList(regionIntroduceQuery);
		List<RegionIntroduceDO> list = new ArrayList<RegionIntroduceDO>();
		RegionIntroduceDO e = new RegionIntroduceDO();
		e.setCityCode(31231);
		e.setContent("eqweqweqweqwe");
		e.setId(1);
		e.setTitle("eqwe");
		e.setType(10);
		
		RegionIntroduceDO e1 = new RegionIntroduceDO();
		e1.setCityCode(31231);
		e1.setContent("eqweqweqweqwe");
		e1.setId(1);
		e1.setTitle("eqwe");
		e1.setType(10);
		
		RegionIntroduceDO e2 = new RegionIntroduceDO();
		e2.setCityCode(31231);
		e2.setContent("eqweqweqweqwe");
		e2.setId(1);
		e2.setTitle("eqwe");
		e2.setType(10);
		list.add(e);
		list.add(e1);
		list.add(e2);
		return list;
	}
	
	
	@Override
	public List<RegionIntroduceDO> getListDestinationShowCaseResult(int type, String cityCode) {
		
		return null;
	}
	
	public List<HotelDO> getListHotelDO(String cityCode){
		HotelPageQuery hotelPageQuery = new HotelPageQuery();
		hotelPageQuery.setRegionId(Long.valueOf(cityCode));
		ICPageResult<HotelDO>  res = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
		if(res !=null && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())){
			return res.getList();
		}
		return null;
		
	}
}
