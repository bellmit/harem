package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.harem.model.vo.ActivityVO;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 活动管理
 * @author czf
 */
@Controller
@RequestMapping("/activityManage")
public class ActivityManageController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;
    /**
     * 活动列表
     * @return 活动列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,ActivityListQuery activityListQuery,PageVo pageVo) throws Exception {
        ActivityVO activityVO = new ActivityVO();
        activityVO.setActivityListQuery(activityListQuery);
        List<Activity> activityList = activityService.getList(activityVO.getActivity());

        pageVo.setTotalSum((long) 14800);
        //pageVo.setCurrentPage(60);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("activityListQuery", activityListQuery);
        model.addAttribute("activityList", activityList);
        return "/system/activity/list";
    }

    /**
     * 根据活动ID获取活动详情
     * @return 活动详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Activity activity = activityService.getById(id);
        model.addAttribute("activity",activity);
        return "/system/activity/detail";
    }

    /**
     * 新增活动
     * @return 活动详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/activity/edit";
    }
    /**
     * 新增活动
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo add(Activity activity) throws Exception {
        activityService.add(activity);
        return new ResponseVo();
    }
    /**
     * 编辑活动
     * @return 活动详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        Activity activity = activityService.getById(id);
        model.addAttribute("activity", activity);
        return "/system/activity/edit";
    }

    /**
     * 编辑俱乐部
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo edit(Activity activity) throws Exception {
        activityService.modify(activity);
        System.out.println(activity.getTagList().get(0));
        return new ResponseVo();
    }

    /**
     * 俱乐部加入状态变更
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setIsStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setJoinStatus(@PathVariable(value = "id")long id,int isStatus) throws Exception {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setIsStatus(isStatus);
        activityService.modify(activity);
        return new ResponseVo();
    }




}
