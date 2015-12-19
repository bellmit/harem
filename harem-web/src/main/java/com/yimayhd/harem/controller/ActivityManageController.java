package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.harem.model.vo.ActivityVO;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

	/**
	 * 活动列表
	 * 
	 * @return 活动列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ActivityListQuery query) throws Exception {
		List<SnsActivityDO> activityList = activityService.getList(query);
		PageVO pageVo = new PageVO(query.getPageNumber(), query.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("activityListQuery", query);
		model.addAttribute("activityList", activityList);
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
	public String toAdd() throws Exception {
		System.out.println("toAdd11111");
		return "/system/activity/edit";
	}

	/**
	 * 新增活动
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Activity activity) throws Exception {
		activityService.add(activity);
		System.out.println("add11111");
		return "/success";
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

	/**
	 * 编辑俱乐部
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(Activity activity) throws Exception {
		activityService.modify(activity);
		System.out.println("edit111111");
		return "/error";
	}

	/**
	 * 俱乐部加入状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setIsStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(@PathVariable(value = "id") long id, int isStatus) throws Exception {
		Activity activity = new Activity();
		activity.setId(id);
		activity.setIsStatus(isStatus);
		activityService.modify(activity);
		return new ResponseVo();
	}

}
