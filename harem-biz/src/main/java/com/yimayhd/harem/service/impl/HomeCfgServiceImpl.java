package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.harem.util.Common;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.recommend.TravelKaItem;
import com.yimayhd.resourcecenter.model.enums.ErrorCode;
import com.yimayhd.resourcecenter.model.resource.LineInfo;
import com.yimayhd.resourcecenter.model.resource.TravelSpecialInfo;
import com.yimayhd.resourcecenter.model.resource.UserInfo;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class HomeCfgServiceImpl implements HomeCfgService{

	private static final String img = "T1xthTB4YT1R4cSCrK.png";
	
	@Autowired
	private ShowcaseClientServer showCaseClientServer;
	
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
	public List<HomeBaseVO> getHomeVipList() {
		return null;
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
        userInfo.setGender(1);
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
	        travelKaItem.id = 233;
	        travelKaItem.avatar = img;
	        travelKaItem.age = 33;
	        travelKaItem.gender = 2;
	        travelKaItem.city = "大理";
	        travelKaItem.province = "云南";
	        travelKaItem.signature = "我想要的生活";
	        travelKaItem.name = "大鹏";
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
	        userInfo.setGender(1);
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
	
	
	

}
