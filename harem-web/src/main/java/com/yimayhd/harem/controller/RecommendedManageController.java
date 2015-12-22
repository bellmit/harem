package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.RegionIntroduce;
import com.yimayhd.harem.service.RecommendedService;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;

@Controller
@RequestMapping("/B2C/recommended")
public class RecommendedManageController extends BaseController {
	
	@Autowired RecommendedService recommendedService;
	
	@RequestMapping(value="/toAdd",method = RequestMethod.GET)
	public String toAdd(Model model){
		return "/system/recommended/edit";
	}
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public String add(RegionIntroduceDO RegionIntroduce){
		boolean flag = false;
		try {
			flag = recommendedService.saveOrUpdate(RegionIntroduce);
			if(flag){
				return "/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/error";
	}

	@RequestMapping("/list")
	public String list(Model model,RegionIntroduceQuery regionIntroduceQuery){
		try {
			List<RegionIntroduceDO> list = recommendedService.queryRegionIntroduceList(regionIntroduceQuery);
			model.addAttribute("list", list);
			return "/system/recommended/list";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/error";
	}
	
	@RequestMapping("/del/{id}")
	@ResponseBody
	public ResponseVo del(Model model, @PathVariable(value = "id") long id){
		boolean flag = recommendedService.delete(id);
		return new ResponseVo(flag);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable(value = "id") long id){
		RegionIntroduceDO regionIntroduceDO = recommendedService.getRegionIntroduceDO(id);
		model.addAttribute("regionIntroduceDO", regionIntroduceDO);
		return "/system/recommended/edit";
	}
	
}
