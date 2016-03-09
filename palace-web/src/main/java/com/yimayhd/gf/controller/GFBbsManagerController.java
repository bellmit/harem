package com.yimayhd.gf.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.gf.service.BbsService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.snscenter.client.domain.SnsMasterDO;
import com.yimayhd.snscenter.client.domain.SnsModuleDO;
import com.yimayhd.snscenter.client.query.SnsMasterPageQuery;
import com.yimayhd.snscenter.client.query.SnsModulePageQuery;
import com.yimayhd.snscenter.client.result.BaseResult;

@Controller
@RequestMapping("/GF/bbs")
public class GFBbsManagerController {
	
	@Autowired
	private BbsService bbsService;

//	@RequestMapping("/module/index")
//	public String moduleIndex(){
//		return "/system/bbs/module";
//	}
	
	
	@RequestMapping("/module/index")
	public String loadMOdel(SnsModulePageQuery bbsModulePageQuery,Integer pageNumber,Model model){
		
		bbsModulePageQuery.setNeedCount(true);
		
		if(null == pageNumber){
			pageNumber = 1;
		}
		bbsModulePageQuery.setPageNo(pageNumber);
		bbsModulePageQuery.setPageSize(10);
		
		PageVO<SnsModuleDO> result = bbsService.moduleQueryList(bbsModulePageQuery);
		
	
		model.addAttribute("query", bbsModulePageQuery);
		model.addAttribute("pageVo", result);
		return "/system/bbs/moduleList";
	}
	
	@RequestMapping("/module/saveOrUpdate")
	@ResponseBody
	public ResponseVo saveOrUpdate(SnsModuleDO bbsModuleDO){
		
		ResponseVo ajaxResponse=new ResponseVo(true);
		if(bbsModuleDO.getId()!=null){ 
			
			BaseResult<SnsModuleDO> updResult = bbsService.updateSnsModule(bbsModuleDO);
			if(!updResult.isSuccess()){
				
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}else{
			
			BaseResult<SnsModuleDO> addResult = bbsService.saveSnsModule(bbsModuleDO);
			if(!addResult.isSuccess()){
				
				ajaxResponse.setMessage(addResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
	}
	
	
	@RequestMapping("/initModule")
	public String initModule(Long id,Model model){
		
		if(null != id && id > 0){
			
			BaseResult<SnsModuleDO> bbsModuleResult = bbsService.selectSnsModuleById(id);
			
			if(!bbsModuleResult.isSuccess()){
				model.addAttribute("module", bbsModuleResult.getValue());
			}
		}
		
		
		return "/system/bbs/addModule";
	}
	
	
	
	@RequestMapping("/master/index")
	public String loadMOdel(SnsMasterPageQuery bbsMasterPageQuery,Integer pageNumber,Model model){
		
		if(null == pageNumber){
			pageNumber = 1;
		}
		
		bbsMasterPageQuery.setNeedCount(true);
		bbsMasterPageQuery.setPageNo(pageNumber);
		bbsMasterPageQuery.setPageSize(10);
		
		PageVO<SnsMasterDO> result = bbsService.masterQueryList(bbsMasterPageQuery);
		
		model.addAttribute("pageVo", result);
		model.addAttribute("query", bbsMasterPageQuery);
	
		return "/system/bbs/masterList";
	}
	
	@RequestMapping("/initMaster")
	public String initMasterUI(String id,Model model){
		
		if(StringUtils.isNotEmpty(id)){
			
			BaseResult<SnsMasterDO> masterResult = bbsService.getBbsMasterById(Long.parseLong(id));
			if(masterResult.isSuccess()){
				
				SnsMasterDO bbsMasterDO = masterResult.getValue();
				model.addAttribute("master", bbsMasterDO);
			}
		}
		
		return "/system/bbs/addMaster";
	}
	
	@RequestMapping("/master/saveOrUpdate")
	@ResponseBody
	public ResponseVo saveOrUpdate(SnsMasterDO bbsMasterDO,String imgUrl){
		
		if(StringUtils.isNotBlank(imgUrl)){
			bbsMasterDO.setAvatar(imgUrl.substring(imgUrl.lastIndexOf("/") + 1));
		}
		
		
		ResponseVo ajaxResponse=new ResponseVo(true);
		if(bbsMasterDO.getId()!=null){ 
			
			BaseResult<SnsMasterDO> updResult = bbsService.updateBbsMaster(bbsMasterDO);
			if(!updResult.isSuccess()){
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}else{
			
			BaseResult<SnsMasterDO> addResult = bbsService.saveBbsMaster(bbsMasterDO);
			if(!addResult.isSuccess()){
				ajaxResponse.setMessage(addResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
			
	}
	
}
