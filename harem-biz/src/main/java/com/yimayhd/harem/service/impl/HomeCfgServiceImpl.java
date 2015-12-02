package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.model.vo.HomeResultInfo;
import com.yimayhd.harem.model.vo.HomeResultVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.harem.util.Common;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.CityInfo;
import com.yimayhd.resourcecenter.model.enums.ErrorCode;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.LineInfo;
import com.yimayhd.resourcecenter.model.resource.TravelKaItem;
import com.yimayhd.resourcecenter.model.resource.TravelSpecialInfo;
import com.yimayhd.resourcecenter.model.resource.UserInfo;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class HomeCfgServiceImpl implements HomeCfgService{

	/**
	 * 会员专享boothid
	 */
	private static final long HOME_CONFIG_VIP_BOOTH_ID = 60;
	
	/**
	 * 大咖带你玩boothid
	 */
	private static final long HOME_CONFIG_LINE_BOOTH_ID = 61;
	
	/**
	 * 金牌旅游咖boothid
	 */
	private static final long HOME_CONFIG_TRAVEL_KA_BOOTH_ID = 62;
	
	/**
	 * 目的地boothid
	 */
	private static final long HOME_CONFIG_CITY_BOOTH_ID = 63;
	
	/**
	 * 品质游记boothid
	 */
	private static final long HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID = 64;
	
	private static final String img = "T1xthTB4YT1R4cSCrK.png";
	
	@Autowired
	private ShowcaseClientServer showCaseClientServer;
	
	@Autowired
	private BoothClientServer boothCilentServer;
	
	@Override
	public RcResult<Boolean> addVipList(HomeBaseVO homeVipVO) {
		RcResult<Boolean> batchInsertShowcase = new RcResult<Boolean>();
		
		if(null == homeVipVO){
			batchInsertShowcase.setErrorCode(ErrorCode.PARAM_ERROR);
		}
		
		BoothDO boothDO = setBoothInfo(homeVipVO); 
		
		List<ShowcaseDO> showcaseList = setShowCaseListBase(homeVipVO);
		
		batchInsertShowcase = showCaseClientServer.batchInsertShowcase(boothDO, showcaseList);
		
		
		return batchInsertShowcase;
	}

	private BoothDO setBoothInfo(HomeBaseVO homeBaseVO) {
		
		BoothDO boothDO = new BoothDO();
		boothDO.setDesc(homeBaseVO.getSubTitle());
		boothDO.setCode(homeBaseVO.getBoothCode());
		boothDO.setId(homeBaseVO.getBoothId());
		
		return boothDO;
	}

	private List<ShowcaseDO> setShowCaseListBase(HomeBaseVO homeVipVO) {
		
		List<ShowcaseDO> showcaseList = new ArrayList<ShowcaseDO>();
		
		ShowcaseDO showcaseDO = null;
		
		long[] vipIds = homeVipVO.getItemIds();
		
		for (int i = 0; i < vipIds.length; i++) {
			
			showcaseDO = new ShowcaseDO();
			//TODO
			if(homeVipVO.getBoothId() > 1){
				showcaseDO.setBoothId(homeVipVO.getBoothId());
			}
			if(!Common.isEmptyArray(homeVipVO.getItemTitle())){
				showcaseDO.setTitle(homeVipVO.getItemTitle()[i]);
			}
			if(!Common.isEmptyArray(homeVipVO.getImgUrl())){
				showcaseDO.setImgUrl(homeVipVO.getImgUrl()[i]);
			}
			if(!Common.isEmptyArray(homeVipVO.getDescription())){
				showcaseDO.setSummary(homeVipVO.getDescription()[i]);
			}
			if(!Common.isEmptyArray(homeVipVO.getItemIds())){
				showcaseDO.setOperationId(homeVipVO.getItemIds()[i]);
			}
			
			showcaseList.add(showcaseDO);
		}
		
		return showcaseList;
	}

	@Override
	public RcResult<Boolean> addLineList(HomeBaseVO homeBaseVO) {
		
        LineInfo lineInfo = new LineInfo();
        lineInfo.setName("线路1");
        lineInfo.setPrice(223);
        lineInfo.setId(1222);
        lineInfo.setLogo_pic(img);
        List<String> tags = new ArrayList<String>();
        tags.add("蜜月之旅");
        tags.add("深呼吸系列");
        
        UserInfo userInfo = new UserInfo();
        userInfo.setId(233);
        userInfo.setName("sam");
        userInfo.setAge(23);
        userInfo.setSignature("云南旅游达人");
        userInfo.setGender("MALE");
        userInfo.setAvatar(img);
        lineInfo.setUserInfo(userInfo);
        System.out.println(JSON.toJSONString(lineInfo));
	
        
        List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
			
		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addTravelKaList(HomeBaseVO homeBaseVO) {
		
	 	TravelKaItem travelKaItem = new TravelKaItem();
        travelKaItem.setId(233);
        travelKaItem.setAvatar(img);
        travelKaItem.setAge(33);;
        travelKaItem.setGender(2);;
        travelKaItem.setCity("大理");
        travelKaItem.setProvince("云南");
        travelKaItem.setSignature("我想要的生活");
        travelKaItem.setName("大鹏");
        System.out.println(JSON.toJSONString(travelKaItem));
        
        List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
		
		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addCityList(HomeBaseVO homeBaseVO) {
		 List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
			BoothDO boothDO = new BoothDO();
			RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addTravelSpecialList(HomeBaseVO homeBaseVO) {
		
		TravelSpecialInfo travelSpecialInfo = new TravelSpecialInfo();
        travelSpecialInfo.setTitle("穿梭在云端的日子");
        travelSpecialInfo.setBackImg(img);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(233);
        userInfo.setName("sam");
        userInfo.setAge(23);
        userInfo.setSignature("云南旅游达人");
        userInfo.setGender("FEMALE");
        userInfo.setAvatar(img);
        travelSpecialInfo.setUserInfo(userInfo);
        travelSpecialInfo.setSupportNum(234);
        travelSpecialInfo.setRedNum(333);


        System.out.println(JSON.toJSONString(travelSpecialInfo));
	
        List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
		
		return insertStatus;
	}
	
	@Override
	public HomeResultVO getVipList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_VIP_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			HomeResultVO homeResultVO = new HomeResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<HomeResultInfo> homeCfgInfoList  = setBaseShowCase(showcaseResult.getList());
				
				homeResultVO.setHomeCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		
		
		return null;
	}

	
	private List<HomeResultInfo> setBaseShowCase(List<ShowCaseResult> showCaseList) {
		
		if(null == showCaseList){
			return null;
		}
		
		List<HomeResultInfo> homeResultInfos = new ArrayList<HomeResultInfo>();
		
		HomeResultInfo homeResultInfo = null;
		
		for (ShowCaseResult showCaseResult : showCaseList) {
			
			ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
			homeResultInfo = new HomeResultInfo();
			
			homeResultInfo.setItemId(showcaseDO.getOperationId());
			homeResultInfo.setItemImg(showcaseDO.getImgUrl());
			homeResultInfo.setItemTitle(showcaseDO.getTitle());
			homeResultInfo.setItemDesc(showcaseDO.getSummary());
			
			homeResultInfos.add(homeResultInfo);
		}
		
		
		return homeResultInfos;
	}
	
	
	@Override
	public HomeResultVO getLineList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_LINE_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			HomeResultVO homeResultVO = new HomeResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_LINE_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_LINE_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<HomeResultInfo> homeCfgInfoList = new ArrayList<HomeResultInfo>();
				
				
				HomeResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new HomeResultInfo();
					
					LineInfo lineInfo = JSON.parseObject(showcaseDO.getOperationContent(),LineInfo.class);
					
					homeResultInfo.setItemId(lineInfo.getId());
					homeResultInfo.setItemTitle(lineInfo.getName());
					homeResultInfo.setItemPrice(lineInfo.getPrice());;
					homeResultInfo.setExtImg(lineInfo.getUserInfo().getAvatar());
					homeResultInfo.setExtName(lineInfo.getUserInfo().getNickname());
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setHomeCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}

	@Override
	public HomeResultVO getTravelKaList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_TRAVEL_KA_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			HomeResultVO homeResultVO = new HomeResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_TRAVEL_KA_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_TRAVEL_KA_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<HomeResultInfo> homeCfgInfoList = new ArrayList<HomeResultInfo>();
				
				
				HomeResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new HomeResultInfo();
					
					TravelKaItem travelKaItem = JSON.parseObject(showcaseDO.getOperationContent(), TravelKaItem.class);
					
					homeResultInfo.setItemId(travelKaItem.getId());
					homeResultInfo.setItemTitle(travelKaItem.getNickname());
					homeResultInfo.setItemImg(travelKaItem.getAvatar());
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setHomeCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}

	@Override
	public HomeResultVO getCityList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_CITY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			HomeResultVO homeResultVO = new HomeResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<HomeResultInfo> homeCfgInfoList = new ArrayList<HomeResultInfo>();
				
				
				HomeResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new HomeResultInfo();
					
					CityInfo cityInfo = JSON.parseObject(showcaseDO.getOperationContent(), CityInfo.class);
					
					homeResultInfo.setItemId(cityInfo.id);
					homeResultInfo.setItemTitle(cityInfo.name);
					homeResultInfo.setItemImg(cityInfo.url);
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setHomeCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		return null;
	}

	@Override
	public HomeResultVO getTravelSpecialList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			HomeResultVO homeResultVO = new HomeResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<HomeResultInfo> homeCfgInfoList = new ArrayList<HomeResultInfo>();
				
				
				HomeResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new HomeResultInfo();
					
					TravelSpecialInfo travelSpecialInfo = JSON.parseObject(showcaseDO.getOperationContent(), TravelSpecialInfo.class);
					
					homeResultInfo.setItemId(travelSpecialInfo.getId());
					homeResultInfo.setItemTitle(travelSpecialInfo.getTitle());
					homeResultInfo.setItemImg(travelSpecialInfo.getBackImg());
					homeResultInfo.setExtName(travelSpecialInfo.getUserInfo().getNickname());
					homeResultInfo.setExtImg(travelSpecialInfo.getUserInfo().getAvatar());
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setHomeCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}
	
	
	

}
