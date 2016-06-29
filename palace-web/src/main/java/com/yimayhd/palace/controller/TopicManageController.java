package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.TopicInfoVO;
import com.yimayhd.palace.model.TopicVO;
import com.yimayhd.palace.model.query.TopicListQuery;
import com.yimayhd.palace.service.TopicService;
import com.yimayhd.palace.tair.TcCacheManager;
import com.yimayhd.snscenter.client.result.topic.TopicResult;
import com.yimayhd.user.session.manager.SessionManager;

/** 
* @ClassName: TopicManageController 
* @Description: 九休话题管理
* @author create by hongfei.guo @ 2016年6月27日 上午11:03:29 
*/

@Controller
@RequestMapping("/jiuxiu/topic")
public class TopicManageController extends BaseController {
	
	@Autowired
	private TopicService topicService;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private TcCacheManager	tcCacheManager;
	
	/**
	 * 话题列表
	 * @return 话题列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, TopicListQuery topicListQuery) throws Exception {
//		PageVO<TopicResult> pageVo = topicService.getTopicPageList(topicListQuery);
//		model.addAttribute("pageVo", pageVo);
		model.addAttribute("topicListQuery", topicListQuery);
		return "/system/topic/list";
	}
	
	/**
	 * 新增话题
	 * 
	 * @return 话题详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/topic/edit";
	}
	
	/**
	 * 新增话题
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(TopicInfoVO topicInfoVO, String uuidHotel) throws Exception {
		
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuidHotel;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {
				TopicVO result = topicService.addTopic(topicInfoVO);
				return  ResponseVo.success(result);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				ResponseVo resVO = ResponseVo.error(e);
				resVO.setData(UUID.randomUUID().toString());
				return resVO;
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}
	
	/**
	 * 编辑话题
	 * 
	 * @return 话题详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		
//		TopicVO topicVO = topicService.getTopicDetailInfo(id);
//		model.addAttribute("hotel", topicVO);
		return "/system/topic/edit";
	}
	
	/**
	 * 编辑话题
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(TopicInfoVO topicInfoVO, @PathVariable("id") long id, String uuidHotel) throws Exception {
		try {
			topicInfoVO.setId(id);
			TopicVO topicVO = topicService.updateTopic(topicInfoVO);
			return  ResponseVo.success(topicVO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
	/**
	 * 启用、停用话题
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTopicStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateTopicStatus(@PathVariable("id") long id, String status) throws Exception {
		try {
			boolean isSuccess = topicService.updateTopicStatus(id, status);
			if(isSuccess){
				return ResponseVo.success();
			}
			return ResponseVo.error();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
	
	/**
	 * 推荐话题列表
	 * @return 推荐话题列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/recommendList", method = RequestMethod.GET)
	public String recommendList(Model model, TopicListQuery topicListQuery) throws Exception {
		
		topicListQuery.setStatus(Constant.TOPIC_STATUS_AVAILABLE);
//		PageVO<TopicResult> pageVo = topicService.getTopicPageList(topicListQuery);
//		model.addAttribute("pageVo", pageVo);
		model.addAttribute("topicListQuery", topicListQuery);
		return "/system/topic/recommendList";
	}
	
	/**
	 * 添加到推荐列表、从推荐列表删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setTopic", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setTopic(String idStr, String status) throws Exception {
		try {
			if(StringUtils.isBlank(idStr) || StringUtils.isBlank(status)){
				return ResponseVo.error();
			}
			
			List<Long> idList = new ArrayList<Long>();
			String[] arr = idStr.split(",");
			for(int i = 0; i < arr.length; i++){
				idList.add(Long.parseLong(arr[i]));
			}
			
			boolean isSuccess = topicService.setTopic(idList, status);
			if(isSuccess){
				return ResponseVo.success();
			}
			return ResponseVo.error();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
}
