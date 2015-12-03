package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.vo.CfgBaseVO;
import com.yimayhd.harem.model.vo.CfgResultInfo;
import com.yimayhd.harem.model.vo.CfgResultVO;
import com.yimayhd.harem.service.PeripheryCfgService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.CityInfo;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.SnsActivePageInfo;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class PeripheryCfgServiceImpl implements PeripheryCfgService{
	
	/**
	 * 俱乐部主题boothid
	 */
	private static final long HOME_CONFIG_CLUB_CATEGORY_BOOTH_ID = 71;
	
	/**
	 * 热门周边boothid
	 */
	private static final long HOME_CONFIG_CITY_BOOTH_ID = 72;
	
	/**
	 * 精彩活动boothid
	 */
	private static final long HOME_CONFIG_ACTIVITY_BOOTH_ID = 73;

	@Autowired
	private ShowcaseClientServer showCaseClientServer;
	@Autowired
	private BoothClientServer boothCilentServer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PeripheryCfgServiceImpl.class);
	
	@Override
	public RcResult<Boolean> addClubCategoryList(CfgBaseVO homeVipVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addCityList(CfgBaseVO homeBaseVO) {
		return null;
	}

	@Override
	public RcResult<Boolean> addActivityList(CfgBaseVO homeBaseVO) {
		return null;
	}

	@Override
	public CfgResultVO getClubCattegoryList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_CLUB_CATEGORY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_CLUB_CATEGORY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_CLUB_CATEGORY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList  = setBaseShowCase(showcaseResult.getList());
				
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		
		
		return null;
	}

	@Override
	public CfgResultVO getActivityList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_ACTIVITY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			
			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_ACTIVITY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_ACTIVITY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					
					SnsActivePageInfo activityInfo = JSON.parseObject(showcaseDO.getOperationContent(), SnsActivePageInfo.class);
					
					homeResultInfo.setItemId(activityInfo.getId());
					homeResultInfo.setItemTitle(activityInfo.getClubName());
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}
		

	@Override
	public CfgResultVO getCityList() {
RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_CITY_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					
					CityInfo cityInfo = JSON.parseObject(showcaseDO.getOperationContent(), CityInfo.class);
					
					homeResultInfo.setItemId(cityInfo.id);
					homeResultInfo.setItemTitle(cityInfo.name);
					homeResultInfo.setItemImg(cityInfo.url);
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		return null;
	}

	
	private List<CfgResultInfo> setBaseShowCase(List<ShowCaseResult> showCaseList) {
		
		if(null == showCaseList){
			return null;
		}
		
		List<CfgResultInfo> homeResultInfos = new ArrayList<CfgResultInfo>();
		
		CfgResultInfo homeResultInfo = null;
		
		for (ShowCaseResult showCaseResult : showCaseList) {
			
			ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
			homeResultInfo = new CfgResultInfo();
			
			homeResultInfo.setItemId(showcaseDO.getOperationId());
			homeResultInfo.setItemImg(showcaseDO.getImgUrl());
			homeResultInfo.setItemTitle(showcaseDO.getTitle());
			homeResultInfo.setItemDesc(showcaseDO.getSummary());
			
			homeResultInfos.add(homeResultInfo);
		}
		
		
		return homeResultInfos;
	}
}
