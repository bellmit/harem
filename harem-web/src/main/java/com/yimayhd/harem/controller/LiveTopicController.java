package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.Live;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.model.query.LiveListQuery;
import com.yimayhd.harem.model.vo.LiveVO;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.service.LiveService;

/**
 * 直播（标签）话题管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/liveTopicManage")
public class LiveTopicController {
	 @Autowired
	 private LiveService liveService;
	
	 /**
     * 直播管理列表
     * @return 直播管理列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,LiveListQuery liveListQuery)  throws Exception {
    	/*LiveVO liveVO = new LiveVO();
    	liveVO.setLiveListQuery(liveListQuery);
    	List<Live> liveList = liveService.getList(liveVO.getLive());
    	model.addAttribute("liveList", liveList);*/
    	/*model.addAttribute("pageVo", pageVo);
    	 model.addAttribute("liveListQuery", liveListQuery);*/
    	return "/system/liveTopic/list";
    }
    
    
    
   /**
     * 直播管理列表
     * @return 添加
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model,LiveListQuery liveListQuery)  throws Exception {
    	/*LiveVO liveVO = new LiveVO();
    	liveVO.setLiveListQuery(liveListQuery);
    	List<Live> liveList = liveService.getList(liveVO.getLive());
    	model.addAttribute("liveList", liveList);
    	model.addAttribute("pageVo", pageVo);
    	 model.addAttribute("liveListQuery", liveListQuery);*/
    	return "/system/liveTopic/add";
    }
    
    /**
     * 根据直播ID获取直播详情
     * @return 直播详情
     * @throws Exception
     *//*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Live live = liveService.getById(id);
        model.addAttribute("live",live);
        return "/system/live/detail";
    }
    
    
    *//**
     * 审核
     * @return
     * @throws Exception
     *//*
    @RequestMapping(value = "/setAudit/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setJoinStatus(@PathVariable(value = "id")long id,String audit) throws Exception {
    
        return new ResponseVo();
    }
    
   */
   
	
}
