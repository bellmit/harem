package com.yimayhd.harem.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.service.RecommendedService;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;

/**
* @ClassName: RecommendedManageController 
* @Description: (特色推荐) 
* @author create by yushengwei @ 2016年1月4日 下午2:19:50
 */
@Controller
@RequestMapping("/B2C/recommended")
public class RecommendedManageController extends BaseController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
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
	public String list(Model model,RegionIntroduceQuery query,Integer pageNumber,Integer pageSize){
		try {
			if (pageNumber != null) {
				query.setPageNo(pageNumber);
			} else {
				query.setPageNo(BaseQuery.DEFAULT_PAGE);
			}
			if(pageSize!= null) {
				query.setPageSize(pageSize);
			} else{
				query.setPageSize(BaseQuery.DEFAULT_SIZE);
			}
			PageVO<RegionIntroduceDO> pageVo = recommendedService.pageVORegionIntroduceDO(query);
			model.addAttribute("query", query);
			model.addAttribute("list", pageVo.getItemList());
			model.addAttribute("pageVo", pageVo);
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
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id){
		RegionIntroduceDO regionIntroduceDO = recommendedService.getRegionIntroduceDO(id);
		model.addAttribute("regionIntroduceDO", regionIntroduceDO);
		model.addAttribute("isEdit", true);
		model.addAttribute("cityId",regionIntroduceDO.getId());
		return "/system/recommended/edit";
	}

	@RequestMapping(value="/edit/{id}",method=RequestMethod.POST)
	public @ResponseBody ResponseVo edit(Model model, @PathVariable(value = "id") long id,RegionIntroduceDO regionIntroduce){
		try {
			regionIntroduce.setId(id);
			boolean flag = recommendedService.saveOrUpdate(regionIntroduce);
			if(flag){
				return new ResponseVo(ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
}
