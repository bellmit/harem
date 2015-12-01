package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.recommend.TravelKaItem;
import com.yimayhd.resourcecenter.model.resource.LineInfo;
import com.yimayhd.resourcecenter.model.resource.TravelSpecialInfo;
import com.yimayhd.resourcecenter.model.resource.UserInfo;

public class HomeCfgServiceImpl implements HomeCfgService{

	private static final String img = "T1xthTB4YT1R4cSCrK.png";
	
	@Override
	public boolean addVipList(HomeBaseVO homeVipVO) {
		
		//TODO
		List<ShowcaseDO> showcaseList = new ArrayList<ShowcaseDO>();
		
		BoothDO boothDO = new BoothDO();
		boothDO.setDesc(homeVipVO.getSubTitle());
		boothDO.setCode("");
		boothDO.setId(1L);
		
		long[] vipIds = homeVipVO.getItemIds();
		
		ShowcaseDO showcaseDO = null;
		
		for (int i = 0; i < vipIds.length; i++) {
			
			showcaseDO = new ShowcaseDO();
			
			showcaseDO.setBoothId(0);
			showcaseDO.setTitle(homeVipVO.getItemTitle()[i]);
			showcaseDO.setImgUrl(homeVipVO.getImgUrl()[i]);
			showcaseDO.setSummary(homeVipVO.getDescription()[i]);
			showcaseDO.setOperationId(homeVipVO.getItemIds()[i]);
			
			showcaseList.add(showcaseDO);
		}
		
		
		return true;
	}

	@Override
	public List<HomeBaseVO> getHomeVipList() {
		return null;
	}

	@Override
	public boolean addLineList(HomeBaseVO homeBaseVO) {
		
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
		
		return true;
	}

	@Override
	public boolean addTravelKaList(HomeBaseVO homeBaseVO) {
		
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
	        
		return true;
	}

	@Override
	public boolean addCityList(HomeBaseVO homeBaseVO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTravelSpecialList(HomeBaseVO homeBaseVO) {
		
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
		
		return true;
	}
	
	
	

}
