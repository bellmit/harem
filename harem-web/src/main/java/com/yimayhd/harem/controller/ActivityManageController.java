package com.yimayhd.harem.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.service.SnsCenterService;



/**
 * 活动管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/activityManage")
public class ActivityManageController extends BaseController {
	@Autowired
	private ActivityService activityService;
	@Resource
	private ComCenterService comCenterServiceRef;
	@Resource
	private SnsCenterService snsCenterService;
	/**
	 * 活动列表
	 * 
	 * @return 活动列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ActivityInfoDTO query) throws Exception {
		
	/*getActivityPage	List<SnsActivityDO> activityList = activityService.getList(query);
		PageVO pageVo = new PageVO(query.getPageNumber(), query.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("activityListQuery", query);
		model.addAttribute("activityList", activityList);*/
		activityService.getList(query);
		return "/system/activity/list";
	}

	/**
	 * 根据活动ID获取活动详情
	 * 
	 * @return 活动详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Activity activity = activityService.getById(id);
		model.addAttribute("activity", activity);
		return "/system/activity/detail";
	}

	/**
	 * 新增活动
	 * 
	 * @return 活动详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.ACTIVETYTAG.name());
		com.yimayhd.snscenter.client.result.BaseResult<List<ClubInfoDO>> clubList = snsCenterService.selectAllClubList(null);

		model.addAttribute("clubList",clubList.getValue());
		model.addAttribute("tagResult",tagResult.getValue());
		return "/system/activity/edit";
	}
	/**
	 * 保存活动
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(ActivityInfoDTO activityInfoDTO) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		com.yimayhd.snscenter.client.result.BaseResult<SnsActivityDO> result = activityService.save(activityInfoDTO);
		if(result.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}


	/**
	 * 编辑活动
	 * 
	 * @return 活动详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		Activity activity = activityService.getById(id);
		model.addAttribute("activity", activity);
		System.out.println("toEdit111111");
		return "/system/activity/edit";
	}



}
