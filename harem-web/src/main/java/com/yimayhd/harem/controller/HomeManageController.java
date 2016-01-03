package com.yimayhd.harem.controller;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.vo.CfgBaseVO;
import com.yimayhd.harem.model.vo.ShowCaseVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.harem.service.ServiceResult;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
  * @autuor : xusq
  * @date : 2015年12月1日
  * @description : 首页管理
  */
@Controller
@RequestMapping("/B2C/homeManage")
public class HomeManageController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeManageController.class);
	
	@Autowired
	private HomeCfgService homecfgService;
	
	private static final int  ADVERTISE_LEFTUP_INDEX= 0;
	private static final int  ADVERTISE_RIGHTUPONE_INDEX= 1;
	private static final int  ADVERTISE_RIGHTUPTWO_INDEX= 2;
	private static final int  ADVERTISE_DOWNONE_INDEX= 3;  
	private static final int  ADVERTISE_DOWNTWO_INDEX= 4;
	private static final int  ADVERTISE_DOWNTHREE_INDEX= 5;
	
	private static final int TRAVELKA_ONE_INDEX = 0;
	private static final int TRAVELKA_TWO_INDEX = 1;
	private static final int TRAVELKA_THREE_INDEX = 2;

	@RequestMapping("/index")
	public String toHomePageManage(Model model){
		//获取首页中的广告页原有配置
		ServiceResult<List<ShowcaseDO>> advertiseResult = homecfgService.getAdvertiseShowcase();
		LOGGER.debug("advertiseResult={}",JSONObject.toJSONString(advertiseResult));
		
		if(advertiseResult.isSuccess() && !CollectionUtils.isEmpty(advertiseResult.getValue())){
			List<ShowcaseDO> advertise = advertiseResult.getValue();
			if(advertise.size() > ADVERTISE_LEFTUP_INDEX){
				model.addAttribute("advertiseLeftup",advertise.get(ADVERTISE_LEFTUP_INDEX));
			}			
			if(advertise.size() > ADVERTISE_RIGHTUPONE_INDEX){
				model.addAttribute("advertiseRightupOne",advertise.get(ADVERTISE_RIGHTUPONE_INDEX));
			}
			
			if(advertise.size() > ADVERTISE_RIGHTUPTWO_INDEX){
				model.addAttribute("advertiseRightupTwo",advertise.get(ADVERTISE_RIGHTUPTWO_INDEX));
			}
			
			if(advertise.size() > ADVERTISE_DOWNONE_INDEX){
				model.addAttribute("advertiseDownOne",advertise.get(ADVERTISE_DOWNONE_INDEX));
			}
			
			if(advertise.size() > ADVERTISE_DOWNTWO_INDEX){
				model.addAttribute("advertiseDownTwo",advertise.get(ADVERTISE_DOWNTWO_INDEX));
			}
			
			if(advertise.size() > ADVERTISE_DOWNTHREE_INDEX){
				model.addAttribute("advertiseDownThree",advertise.get(ADVERTISE_DOWNTHREE_INDEX));
			}
		}else{
			LOGGER.error("getAdvertiseShowcase error,advertiseResult={}",JSONObject.toJSONString(advertiseResult));
		}
		
		//设置广告位信息
		//获取3个大咖的配置
		ServiceResult<List<ShowcaseDO>> travelKasResult = homecfgService.getTravelKa();
		LOGGER.debug("travelKasResult={}",JSONObject.toJSONString(travelKasResult));
		
		if(travelKasResult.isSuccess() && !CollectionUtils.isEmpty(travelKasResult.getValue())){
			List<ShowcaseDO> travelKas = travelKasResult.getValue();
			if(travelKas.size() > TRAVELKA_ONE_INDEX){
				model.addAttribute("travelKa1",travelKas.get(TRAVELKA_ONE_INDEX));
			}
			
			if(travelKas.size() > TRAVELKA_TWO_INDEX){
				model.addAttribute("travelKa2",travelKas.get(TRAVELKA_TWO_INDEX));
			}
			
			if(travelKas.size() > TRAVELKA_THREE_INDEX){
				model.addAttribute("travelKa3",travelKas.get(TRAVELKA_THREE_INDEX));
			}
		}else{
			LOGGER.error("getTravelKa error,travelKasResult={}",JSONObject.toJSONString(travelKasResult));
		}
		
		//获取精彩推荐配置
		ServiceResult<List<ShowcaseDO>> recommendsResult = homecfgService.getRecommends();
		LOGGER.debug("recommendsResult={}",JSONObject.toJSONString(recommendsResult));
		
		if(recommendsResult.isSuccess() && !CollectionUtils.isEmpty(recommendsResult.getValue())){
			model.addAttribute("recommends",recommendsResult.getValue());
		}else{
			LOGGER.error("getRecommends error,recommendsResult={}",JSONObject.toJSONString(recommendsResult));
		}

		return "/system/homeCfg/homeIndex";
	}
	
/**	
	private void getResultVOs(Map<String, CfgResultVO> homeResults) {
		
//		CfgResultVO vipList = homecfgService.getVipList();
		CfgResultVO lineList = homecfgService.getLineList();
		CfgResultVO travelKaList = homecfgService.getTravelKaList();
//		CfgResultVO cityList = homecfgService.getCityList();
//		CfgResultVO travelSpecialList = homecfgService.getTravelSpecialList();

//		CfgResultVO greatRecommentList = homecfgService.getGreatRecommentList(); // 精彩推荐
		
//		homeResults.put("vipList", vipList);
		homeResults.put("lineList", lineList);  // 精彩推荐
		homeResults.put("travelKaList", travelKaList);
//		homeResults.put("cityList", cityList);
//		homeResults.put("travelSpecialList", travelSpecialList);


	}
*/	
	
	
	
	/**
	 * 会员专享
	 */
	@RequestMapping(value="/addHomeVip",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeVip(CfgBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		RcResult<Boolean> addVipstatus  = homecfgService.addVipList(homeBaseVO);
		
		if(addVipstatus.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(addVipstatus.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	/**
	 * 配置大咔
	 */
	@RequestMapping(value="/addRecommends",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addRecommends(@RequestBody ShowCaseVO [] advertiseList){
		LOGGER.debug("advertiseList={}",JSONObject.toJSONString(advertiseList));
		
		ResponseVo responseVo = new ResponseVo();
		ServiceResult<Boolean> result = homecfgService.addRecommends(Arrays.asList(advertiseList));
		LOGGER.debug("result={}",JSONObject.toJSONString(result));
		
		if(result.isSuccess() == true){
			responseVo.setMessage("操作成功!");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(result.getErrorMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			LOGGER.error("addRecommends error,result={},advertiseList={}",JSONObject.toJSONString(result),JSONObject.toJSONString(advertiseList));
		}
		
		return responseVo;
	}
	
	/**
	 * 旅游咖
	 */
	@RequestMapping(value="/addHomeTravelKa",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeTravelKa(@RequestBody ShowCaseVO [] travelKaList){
		LOGGER.debug("travelKaList={}",JSONObject.toJSONString(travelKaList));
		
		ResponseVo responseVo = new ResponseVo();
		ServiceResult<Boolean> result = homecfgService.addTravelKaList(Arrays.asList(travelKaList));
		LOGGER.debug("result={}",JSONObject.toJSONString(result));
		
		if(result.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(result.getErrorMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			
			LOGGER.error("addTravelKaList error,result={},travelKaList={}",JSONObject.toJSONString(result),JSONObject.toJSONString(travelKaList));
		}
		
		return responseVo;
	}
	
	/**
	 * 目的地
	 */
	@RequestMapping(value="/addHomeCity",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeCity(CfgBaseVO homeBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addCityStatus = homecfgService.addCityList(homeBaseVO);
		
		if(addCityStatus.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(addCityStatus.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	/**
	 * 游记
	 */
	@RequestMapping(value="/addHomeTravelSpecial",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeTravelSpecial(CfgBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addStatus = homecfgService.addTravelSpecialList(homeBaseVO);
		
		if(addStatus.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(addStatus.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	
	/**
	 *	广告位配置
	 * @param advertiseList
	 * @return
	 */
	@RequestMapping(value="/addAdvertise",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo  addAdvertise( @RequestBody ShowCaseVO [] advertiseList ){
		LOGGER.debug("advertiseList={}",JSONObject.toJSONString(advertiseList));
		
		ResponseVo responseVo = new ResponseVo();
		
		ServiceResult<Boolean>  result = homecfgService.addAdvertise(Arrays.asList(advertiseList));
		if(result.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(result.getErrorMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			
			LOGGER.error("addTravelKaList error,result={},advertiseList={}",JSONObject.toJSONString(result),JSONObject.toJSONString(advertiseList));
		}
		
		return responseVo;
	}
	
	
}
