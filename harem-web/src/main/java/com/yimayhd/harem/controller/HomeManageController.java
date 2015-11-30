package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.service.HomeCfgService;

/**
 * 首页管理
 * @author xusq
 *
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
	
	public ResponseVo addHomeCity(HomeBaseVO homeBaseVO){
		ResponseVo responseVo = new ResponseVo();
		
		boolean addCityStatus = homecfgService.addCityList(homeBaseVO);
		
		if(!addCityStatus){
			responseVo.setStatus(500);
			responseVo.setMessage("服务器错误！");
		}
		
		
		
		return responseVo;
	}
}
