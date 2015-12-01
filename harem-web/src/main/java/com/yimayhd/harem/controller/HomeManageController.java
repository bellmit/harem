package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.service.HomeCfgService;

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
	public String toHomePageManage(){
		return "/system/home/index";
	}
	
	/**
	 * 会员专享
	 */
	@RequestMapping("/addHomeVip")
	@ResponseBody
	public ResponseVo addHomeVip(HomeBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		boolean addVipstatus  = homecfgService.addVipList(homeBaseVO);
		
		if(!addVipstatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		return responseVo;
	}
	
	/**
	 * 大咖带你玩
	 */
	@RequestMapping("/addHomeLine")
	@ResponseBody
	public ResponseVo addHomeLineInfo(HomeBaseVO homeBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		boolean addLineStatus = homecfgService.addLineList(homeBaseVO);
		
		if(!addLineStatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		return responseVo;
	}
	
	/**
	 * 旅游咖
	 */
	@RequestMapping("/addHomeTravelKa")
	@ResponseBody
	public ResponseVo addHomeTravelKa(HomeBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		
		boolean addTravelKaStatus = homecfgService.addTravelKaList(homeBaseVO);
		
		if(!addTravelKaStatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		
		return responseVo;
	}
	
	/**
	 * 目的地
	 */
	@RequestMapping("/addHomeCity")
	@ResponseBody
	public ResponseVo addHomeCity(HomeBaseVO homeBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		boolean addCityStatus = homecfgService.addCityList(homeBaseVO);
		
		if(!addCityStatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		
		
		return responseVo;
	}
	
	/**
	 * 游记
	 */
	@RequestMapping("/addHomeTravelSpecial")
	@ResponseBody
	public ResponseVo addHomeTravelSpecial(HomeBaseVO homeBaseVO){
		
		ResponseVo responseVo = new ResponseVo();
		
		boolean addCityStatus = homecfgService.addTravelSpecialList(homeBaseVO);
		
		if(!addCityStatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		
		
		return responseVo;
	}
	
	
	
}
