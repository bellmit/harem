package com.yimayhd.harem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.vo.CfgBaseVO;

/**
  * @autuor : xusq
  * @date : 2015年12月3日
  * @description : 发现管理
  */
@Controller
@RequestMapping("/B2C/discoveryManage")
public class DiscoveryManageController extends BaseController{

	@RequestMapping("/index")
	public String toDiscoveryIndex(Model model){
		
		return "/system/homeCfg/discoveryIndex";
	}
	

	/**
	  * @date : 2015年12月3日
	  * @description : 伴手礼
	  */
	@RequestMapping("/addDiscoveryItem")
	@ResponseBody
	public ResponseVo addDiscoveryItem(CfgBaseVO baseVO){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
	}
	
	/**
	  * @date : 2015年12月3日
	  * @description : 品质游记
	  */
	@RequestMapping("/addDiscoveryTravelSpecial")
	@ResponseBody
	public ResponseVo addDiscoveryTravelSpecial(){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
	}
	
	@RequestMapping("/addDisoverySubject")
	@ResponseBody
	public ResponseVo addDisoverySubject(){
		ResponseVo responseVo = new ResponseVo();
		
		return responseVo;
		
	}
}
