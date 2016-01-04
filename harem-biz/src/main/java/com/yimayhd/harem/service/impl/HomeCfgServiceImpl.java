package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.resourcecenter.model.enums.*;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.vo.CfgBaseVO;
import com.yimayhd.harem.model.vo.CfgResultInfo;
import com.yimayhd.harem.model.vo.CfgResultVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.harem.util.Common;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.CityInfo;
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
import org.springframework.util.StringUtils;

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

	@Autowired
	private UserRPCService userRPCService;

	@Autowired
	BoothClientServer boothClientServerRef;

	@Autowired
	private SnsCenterService snsCenterService;


	
	@Override
	public RcResult<Boolean> addVipList(CfgBaseVO cfgBaseVO) {
		RcResult<Boolean> batchInsertShowcase = new RcResult<Boolean>();
		
		if(null == cfgBaseVO){
			batchInsertShowcase.setErrorCode(ErrorCode.PARAM_ERROR);
		}
		
		BoothDO boothDO = setBoothInfo(cfgBaseVO); 
		
		List<ShowcaseDO> showcaseList = setShowCaseListBase(cfgBaseVO);
		
		batchInsertShowcase = showCaseClientServer.batchInsertShowcase(boothDO, showcaseList);
		
		
		return batchInsertShowcase;
	}

	private BoothDO setBoothInfo(CfgBaseVO cfgBaseVO) {
		
		BoothDO boothDO = new BoothDO();
		boothDO.setDesc(cfgBaseVO.getSubTitle());
		boothDO.setCode(cfgBaseVO.getBoothCode());
		boothDO.setId(cfgBaseVO.getBoothId());
		
		return boothDO;
	}

	private List<ShowcaseDO> setShowCaseListBase(CfgBaseVO cfgBaseVO) {
		
		List<ShowcaseDO> showcaseList = new ArrayList<ShowcaseDO>();
		
		ShowcaseDO showcaseDO = null;
		
		long[] vipIds = cfgBaseVO.getItemIds();
		
		for (int i = 0; i < vipIds.length; i++) {
			
			showcaseDO = new ShowcaseDO();
			//TODO
			if(cfgBaseVO.getBoothId() > 1){
				showcaseDO.setBoothId(cfgBaseVO.getBoothId());
			}
			if(!Common.isEmptyArray(cfgBaseVO.getItemTitle())){
				showcaseDO.setTitle(cfgBaseVO.getItemTitle()[i]);
			}
			if(!Common.isEmptyArray(cfgBaseVO.getImgUrl())){
				showcaseDO.setImgUrl(cfgBaseVO.getImgUrl()[i]);
			}
			if(!Common.isEmptyArray(cfgBaseVO.getDescription())){
				showcaseDO.setSummary(cfgBaseVO.getDescription()[i]);
			}
			if(!Common.isEmptyArray(cfgBaseVO.getItemIds())){
				showcaseDO.setOperationId(cfgBaseVO.getItemIds()[i]);
			}
			
			showcaseList.add(showcaseDO);
		}
		
		return showcaseList;
	}

	@Override
	public RcResult<Boolean> addLineList(CfgBaseVO cfgBaseVO) {
		
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
	public RcResult<Boolean> addTravelKaList(CfgBaseVO cfgBaseVO) {
		RcResult rcResult = new RcResult();
		if(StringUtils.isEmpty(cfgBaseVO.getAddItemIds())){
			return null;
		}
		String itemIds = cfgBaseVO.getAddItemIds();
		String arr [] = itemIds.split(",");
		String code = ColumnType.TRAVE_MASTER.name();  // 大咖code
		BoothDO boothDO = getBothByCode(code);
		List<ShowcaseDO> showcaseDOs = new ArrayList<ShowcaseDO>();
		for(String id : arr){
			ShowcaseDO showcaseDO = new ShowcaseDO();
			showcaseDO.setTitle(ColumnType.TRAVE_MASTER.getCode());
			showcaseDO.setStatus(ShowcaseStauts.ONLINE.getStatus());
			showcaseDO.setShowType(ShowcaseShowType.DEFAULT.getShowType());
			showcaseDO.setOperationContent(id);
			showcaseDO.setBoothId(boothDO.getId());
			showcaseDOs.add(showcaseDO);
		}
		boolean flag = showCaseClientServer.batchSaveShowcase(showcaseDOs);  // 批量保存
		if(flag){
			rcResult.setSuccess(true);
			return rcResult;
		}
		rcResult.setSuccess(false);
		return rcResult;
	}

	@Override
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO) {
		 List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
			BoothDO boothDO = new BoothDO();
			RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO) {
		
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
	public CfgResultVO getVipList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_VIP_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList  = setBaseShowCase(showcaseResult.getList());
				
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
	
	
	@Override
	public CfgResultVO getLineList() {
		String code = ColumnType.GREAT_RECOMMENT.name();  // 精彩推荐
		BoothDO boothDO = getBothByCode(code);
		if(StringUtils.isEmpty(boothDO)){
			return null;
		}
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(boothDO.getId());
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
//			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();

					String operationContent = showcaseDO.getOperationContent();

					long targetId = 0;
					if(!StringUtils.isEmpty(operationContent)){
						targetId = Long.parseLong(operationContent);
					}
					BaseResult<SnsActivityDO> snsActivityDOBaseResult =  snsCenterService.getActivityInfoByActivityId(targetId);
//					LineInfo lineInfo = JSON.parseObject(showcaseDO.getOperationContent(),LineInfo.class);
					
//					homeResultInfo.setItemId(lineInfo.getId());
//					homeResultInfo.setItemTitle(lineInfo.getName());
//					homeResultInfo.setItemPrice(lineInfo.getPrice());;
//					homeResultInfo.setExtImg(lineInfo.getUserInfo().getAvatar());
//					homeResultInfo.setExtName(lineInfo.getUserInfo().getNickname());

					if(snsActivityDOBaseResult != null && snsActivityDOBaseResult.isSuccess()){
						SnsActivityDO snsActivityDO = snsActivityDOBaseResult.getValue();
						homeResultInfo.setItemId(snsActivityDO.getId());
						homeResultInfo.setItemTitle(snsActivityDO.getTitle());
						homeResultInfo.setItemPrice(snsActivityDO.getPreferentialPrice());;
					}
					homeCfgInfoList.add(homeResultInfo);
				}
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}

	@Override
	public CfgResultVO getTravelKaList() {
		String code = ColumnType.TRAVE_MASTER.name();  // 大咖code
		BoothDO boothDO = getBothByCode(code);
		if(StringUtils.isEmpty(boothDO)){
			return null;
		}
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());
		
		if(boothResult.isSuccess()){

			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(boothDO.getId());
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
//			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					String operationContent = showcaseDO.getOperationContent();
					long userId = 0;
					if(!StringUtils.isEmpty(operationContent)){
						userId = Long.parseLong(operationContent);
					}

					UserDO userDO = userRPCService.getUserById(userId);

//					TravelKaItem travelKaItem = JSON.parseObject(showcaseDO.getOperationContent(), TravelKaItem.class);
					
//					homeResultInfo.setItemId(travelKaItem.getId());
//					homeResultInfo.setItemTitle(travelKaItem.getNickname());
//					homeResultInfo.setItemImg(travelKaItem.getAvatar());

					homeResultInfo.setItemId(userDO.getId());
					homeResultInfo.setItemTitle(userDO.getNickname());
					homeResultInfo.setItemImg(userDO.getAvatar());
					
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

	@Override
	public CfgResultVO getTravelSpecialList() {
		
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
		
		if(boothResult.isSuccess()){
			

			
			CfgResultVO homeResultVO = new CfgResultVO();
			
			ShowcaseQuery showCaseQuery = new ShowcaseQuery();
			
			showCaseQuery.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
			
			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);
			
			BoothDO boothDO = boothResult.getT();
			
			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);
			
			if(showcaseResult.isSuccess()){
				
				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();
				
				
				CfgResultInfo homeResultInfo = null;
				
				List<ShowCaseResult> showCaseList = showcaseResult.getList();
				
				for (ShowCaseResult showCaseResult : showCaseList) {
					
					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					
					TravelSpecialInfo travelSpecialInfo = JSON.parseObject(showcaseDO.getOperationContent(), TravelSpecialInfo.class);
					
					homeResultInfo.setItemId(travelSpecialInfo.getId());
					homeResultInfo.setItemTitle(travelSpecialInfo.getTitle());
					homeResultInfo.setItemImg(travelSpecialInfo.getBackImg());
					homeResultInfo.setExtName(travelSpecialInfo.getUserInfo().getNickname());
					homeResultInfo.setExtImg(travelSpecialInfo.getUserInfo().getAvatar());
					
					homeCfgInfoList.add(homeResultInfo);
				}
				
				
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}
			
			return homeResultVO;
		}
		
		return null;
	}





	private BoothDO getBothByCode(String code){
		BoothDO boothDO = null;
		boothDO = boothClientServerRef.getBoothDoByCode(code);
		if(null == boothDO){
			return null;
		}
		return boothDO;
	}

	/**
	 * 查询精彩推荐
	 * @return
	 */
	public CfgResultVO getGreatRecommentList() {
		String code = ColumnType.GREAT_RECOMMENT.name();  // 精彩推荐
		BoothDO boothDO = getBothByCode(code);
		if(StringUtils.isEmpty(boothDO)){
			return null;
		}
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());

		if(boothResult.isSuccess()){

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(boothDO.getId());

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());

			if(showcaseResult.isSuccess()){

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();


				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					String operationContent = showcaseDO.getOperationContent();
					String businessCode = showcaseDO.getBusinessCode();
					long targetId = 0;
					if(!StringUtils.isEmpty(operationContent)){
						targetId = Long.parseLong(operationContent);
					}

					if(businessCode.equals("ACTIVITY")){  // TODO 需要 询问 使用枚举
						// TODO	 调用活动接口
					}else {
						// todo 调用 线路 接口
					}
					homeResultInfo.setItemId(1);
					homeResultInfo.setItemTitle("昆大理");
					homeResultInfo.setItemDesc("180起");
					homeCfgInfoList.add(homeResultInfo);
				}


				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}
	
	
	

}
