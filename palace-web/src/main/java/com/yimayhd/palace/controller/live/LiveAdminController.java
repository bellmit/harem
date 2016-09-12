package com.yimayhd.palace.controller.live;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.gf.model.query.LiveQueryVO;
import com.yimayhd.gf.model.query.ReplayQueryVO;
import com.yimayhd.gf.model.query.RoomQueryVO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.ResponseVo;

/**
 * @ClassName: LiveAdminController
 * @Description: 直播管理
 * @author zhangxiaoyang
 * @date 2016年9月12日
 */
@RequestMapping("/jiuxiu/liveAdmin/")
public class LiveAdminController  extends BaseController {

	private static Logger logger = LoggerFactory.getLogger("LiveAdminController");
//	@Autowired
//	private live
	@RequestMapping(value="liveList",method=RequestMethod.POST)
	public String liveList(Model model,LiveQueryVO queryVO) {
		
		return "";
		
		
	}
	
	@RequestMapping(value="updateLiveWeight",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateLiveWeight(Model model,int liveId,int weight) {
		return null;
		
	}
	
	@RequestMapping(value="replayManageList",method=RequestMethod.POST)
	public String replayManageList(Model model,ReplayQueryVO queryVO) {
		return "";
		
	}
	@RequestMapping(value="updateReplayWeight",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateReplayWeight(Model model,int replayId,int weight) {
		return null;
		
	}
	@RequestMapping(value="updateReplayStatus",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateReplayStatus(Model model,int replayId,String status) {
		return null;
		
	}
	
	@RequestMapping(value="roomList",method=RequestMethod.POST)
	public String roomManageList(Model model,RoomQueryVO queryVO) {
		return "";
		
	}
	
	@RequestMapping(value="updateRoomStatus",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateRoomStatus(Model model,int roomId,Integer closeHour) {
		return null;
		
	}
	@RequestMapping(value="liveClassifyList",method=RequestMethod.POST)
	public String liveClassifyList(Model model) {
		return "";
		
	}
	@RequestMapping(value="addLiveClassify",method=RequestMethod.POST)
	public String addLiveClassify(Model model,String classifyName,Integer classifyId) {
		return "";
		
	}
	@RequestMapping(value="updateClassifyStatus",method=RequestMethod.POST)
	public String updateClassifyStatus(Model model,int classifyId,String status) {
		return "";
		
	}
	@RequestMapping(value="updateClassifyWeight",method=RequestMethod.POST)
	public String updateClassifyWeight(Model model,int classifyId,int weight) {
		return "";
		
	}
	
}
