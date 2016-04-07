package com.yimayhd.gf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.gf.model.BbsPostsQueryVO;
import com.yimayhd.gf.service.BbsService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.snscenter.client.domain.SnsMasterDO;
import com.yimayhd.snscenter.client.domain.SnsModuleDO;
import com.yimayhd.snscenter.client.domain.SnsPostsDO;
import com.yimayhd.snscenter.client.dto.PostsResultDTO;
import com.yimayhd.snscenter.client.query.SnsMasterPageQuery;
import com.yimayhd.snscenter.client.query.SnsModulePageQuery;
import com.yimayhd.snscenter.client.query.SnsPostsQuery;
import com.yimayhd.snscenter.client.result.BaseResult;

@Controller
@RequestMapping("/GF/bbs")
public class GFBbsManagerController {
	
	@Autowired
	private BbsService bbsService;
	
	
	@RequestMapping("/module/index")
	public String loadMOdel(SnsModulePageQuery bbsModulePageQuery,Integer pageNumber,Model model){
		
		bbsModulePageQuery.setNeedCount(true);
		
		if(null == pageNumber){
			pageNumber = 1;
		}
		bbsModulePageQuery.setPageNo(pageNumber);
		
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
			
			if(bbsModuleResult.isSuccess()){
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
	
	
	
	@RequestMapping("/list")
	public String toBbsMgmt(Model model,BbsPostsQueryVO postsQuery){
		
		
		
		
		SnsModulePageQuery bbsModulePageQuery = new SnsModulePageQuery();
		bbsModulePageQuery.setPageNo(1);
		bbsModulePageQuery.setPageSize(100);
		
		PageVO<SnsModuleDO> queryListResult = bbsService.moduleQueryList(bbsModulePageQuery);
		if(null != queryListResult){
			model.addAttribute("modules", queryListResult.getItemList());
			
		}
		PageVO<PostsResultDTO> pageVO = bbsService.postsQueryList(postsQuery);
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("query", postsQuery);
		return "/system/bbs/postsList";
	}
	
	@RequestMapping("/initPosts")
	public String initPosts(String id,Model model){
		
		if(StringUtils.isNotEmpty(id)){
			
			BaseResult<SnsPostsDO> masterResult = bbsService.getPostsDetail(Long.parseLong(id));
			if(masterResult.isSuccess()){
				
				SnsPostsDO posts = masterResult.getValue();
				model.addAttribute("posts", posts);
			}
		}
		
		SnsMasterPageQuery snsMasterPageuery = new SnsMasterPageQuery();

		snsMasterPageuery.setPageNo(1);
		snsMasterPageuery.setPageSize(100);
		
		
		PageVO<SnsMasterDO> masterQueryList = bbsService.masterQueryList(snsMasterPageuery );
		
		
		if(null != masterQueryList){
			model.addAttribute("masters", masterQueryList.getItemList());
		}
		
		SnsModulePageQuery snsModulePageQuery = new SnsModulePageQuery();
		snsModulePageQuery.setPageNo(1);
		snsModulePageQuery.setPageSize(100);
		
		PageVO<SnsModuleDO> moduleQueryList = bbsService.moduleQueryList(snsModulePageQuery);
	    
		
		if(null != moduleQueryList){
			model.addAttribute("module", moduleQueryList.getItemList());
		}
		return "/system/bbs/addPosts";
	}
	
	
	@RequestMapping("/posts/saveOrUpdate")
	@ResponseBody
	public ResponseVo postsSaveOrUpdate(SnsPostsDO snsPosts,String imgUrl){
		
		
		ResponseVo ajaxResponse=new ResponseVo(true);
		if(snsPosts.getId() != null){ 
			
			BaseResult<SnsPostsDO> updResult = bbsService.updatePosts(snsPosts);
			if(!updResult.isSuccess()){
				ajaxResponse.setMessage(updResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}else{
			snsPosts.setStatus(20);
			BaseResult<SnsPostsDO> addResult = bbsService.savePosts(snsPosts);
			if(!addResult.isSuccess()){
				ajaxResponse.setMessage(addResult.getResultMsg());
				ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			}
		}
		return ajaxResponse;
			
	}
	
	
	@RequestMapping("/posts/upOrDown")
	@ResponseBody
	public ResponseVo downPost(Model model,SnsPostsDO bbsPostsDO) {
		
		ResponseVo ajaxResponse = new ResponseVo(true);
		
		bbsPostsDO.setOnlineTime(new Date());
		
		BaseResult<Boolean>  updateStatusResult = bbsService.updatePostsStatus(bbsPostsDO);
		
		if(!updateStatusResult.isSuccess() || !updateStatusResult.getValue()){
			ajaxResponse.setMessage(updateStatusResult.getResultMsg());
			ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
		}
		   

		 return ajaxResponse;
	}
	
	@RequestMapping("/posts/del")
	@ResponseBody
	public ResponseVo deleteBbs(Model model,long postsId) {
		
		ResponseVo ajaxResponse = new ResponseVo(true);
		
		SnsPostsDO bbsPostsDO =  new SnsPostsDO();
		
		bbsPostsDO.setId(postsId);
		bbsPostsDO.setStatus(30);

		BaseResult<Boolean>  updateStatusResult = bbsService.updatePostsStatus(bbsPostsDO);
		
		if(!updateStatusResult.isSuccess()){
			ajaxResponse.setMessage(updateStatusResult.getResultMsg());
			ajaxResponse.setStatus(ResponseStatus.ERROR.VALUE);
			
		}
		


		 return ajaxResponse;
	}
}
