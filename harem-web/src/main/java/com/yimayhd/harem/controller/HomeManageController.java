package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.GenericTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.model.vo.HomeResultVO;
import com.yimayhd.harem.service.HomeCfgService;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
  * @autuor : xusq
  * @date : 2015年12月1日
  * @description : 首页管理
  */
@Controller
@RequestMapping("/B2C/homeManage")
public class HomeManageController extends BaseController{
	
	@Autowired
	private HomeCfgService homecfgService;

	@RequestMapping("/index")
	public String toHomePageManage(Model model){
		
		Map<String, HomeResultVO> homeResults = new HashMap<>();
		
		HomeResultVO vipList = homecfgService.getVipList();
		HomeResultVO lineList = homecfgService.getLineList();
		HomeResultVO travelKaList = homecfgService.getTravelKaList();
		HomeResultVO cityList = homecfgService.getCityList();
		HomeResultVO travelSpecialList = homecfgService.getTravelSpecialList();
		
		homeResults.put("vipList", vipList);
		homeResults.put("lineList", lineList);
		homeResults.put("travelKaList", travelKaList);
		homeResults.put("cityList", cityList);
		homeResults.put("travelSpecialList", travelSpecialList);
		
		model.addAttribute("homeCfg", homeResults);
		
		return "/system/home/index";
	}
	
	/**
	 * 会员专享
	 */
	@RequestMapping(value="/addHomeVip",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeVip(HomeBaseVO homeBaseVO){
		
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
	 * 大咖带你玩
	 */
	@RequestMapping(value="/addHomeLine",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeLineInfo(HomeBaseVO homeBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addLineStatus = homecfgService.addLineList(homeBaseVO);
		
		if(addLineStatus.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(addLineStatus.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	/**
	 * 旅游咖
	 */
	@RequestMapping(value="/addHomeTravelKa",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeTravelKa(HomeBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		
		RcResult<Boolean> addTravelKaStatus = homecfgService.addTravelKaList(homeBaseVO);
		
		if(addTravelKaStatus.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(addTravelKaStatus.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	/**
	 * 目的地
	 */
	@RequestMapping(value="/addHomeCity",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo addHomeCity(HomeBaseVO homeBaseVO){
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
	public ResponseVo addHomeTravelSpecial(HomeBaseVO homeBaseVO){
		
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
	
	
	
}
