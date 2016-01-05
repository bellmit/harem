package com.yimayhd.harem.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.json.Json;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.ActivityVO;
import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.snscenter.client.domain.ActivityJsonDO;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
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
	@Resource
	private CommodityService commodityService;
	
	/**
	 * 活动列表
	 * 
	 * @return 活动列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ActivityListQuery query) throws Exception {

		ActivityQueryDTO activityInfoDTO = convertQuery(query);

		List<SnsActivityDO> activityList = null;
		int totalCount = 0;
		BasePageResult<SnsActivityDO> ret = activityService.getList(activityInfoDTO);
		if (ret != null) {
			activityList = ret.getList();
			totalCount = ret.getTotalCount();
		}
		/*List<SnsActivityDO> list = ret.getList();
		for (SnsActivityDO snsActivityDO : list) {
			BaseResult<List<ComTagDO>> tagResultCheck =activityService.getTagInfoByOutIdAndType(snsActivityDO.getId(),TagType.ACTIVETYTAG.name() );
			
		}*/
		
		PageVO pageVo = new PageVO(query.getPageNumber(), query.getPageSize(), totalCount);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("activityListQuery", query);
		model.addAttribute("activityList", activityList);
		return "/system/activity/list";
	}

	private ActivityQueryDTO convertQuery(ActivityListQuery query) {
		ActivityQueryDTO activityQueryDTO = new ActivityQueryDTO();
		if (query == null) {
			return activityQueryDTO;
		}
		if (query.getPageNumber() != null) {
			activityQueryDTO.setPageNo(query.getPageNumber());
		}
		if (query.getPageSize() != null) {
			activityQueryDTO.setPageSize(query.getPageSize());
		}
		activityQueryDTO.setTitle(query.getTitle());
//		activityQueryDTO.set
		if (query.getProductId() != null) {
			activityQueryDTO.setClubId(query.getProductId());
		}
		if (StringUtils.isNotBlank(query.getActivityBeginDate())) {
			Date startTime = DateUtil.parseDate(query.getActivityBeginDate());
			activityQueryDTO.setActivityStartTime(startTime);
		}
		if (StringUtils.isNotBlank(query.getActivityEndDate())) {
			Date endTime = DateUtil.parseDate(query.getActivityEndDate());
			activityQueryDTO.setActivityEndTime(DateUtil.add23Hours(endTime));
		}
		if (StringUtils.isNotBlank(query.getCreateStartTime())) {
			Date startTime = DateUtil.parseDate(query.getCreateStartTime());
			activityQueryDTO.setStartTime(startTime);
		}
		if (StringUtils.isNotBlank(query.getCreateEndTime())) {
			Date endTime = DateUtil.parseDate(query.getCreateEndTime());
			activityQueryDTO.setEndTime(DateUtil.add23Hours(endTime));
		}
		if (query.getStatus() != null) {
			activityQueryDTO.setState(query.getStatus());
		}
		return activityQueryDTO;
	}

	/**
	 * 根据活动ID获取活动详情
	 * 
	 * @return 活动详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		com.yimayhd.snscenter.client.result.BaseResult<SnsActivityDO> activity = activityService.getById(id);
		model.addAttribute("activity", activity.getValue());
		return "/system/activity/detail";
	}

	/**
	 * 新增活动
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.ACTIVETYTAG.name());
		com.yimayhd.snscenter.client.result.BaseResult<List<ClubInfoDO>> clubList = snsCenterService.selectAllClubList(null);
		model.addAttribute("itemType",ItemType.ACTIVITY.getValue());
		model.addAttribute("clubList",clubList.getValue());
		model.addAttribute("tagResult",tagResult.getValue());
		return "/system/activity/edit";
	}
	/**
	 * 保存活动
	 * 
	 * @return
	 * @throws Exception  public BaseResult<SnsActivityDO> updateActivityInfo(ActivityInfoDTO activityInfoDTO)
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(ActivityVO activityVO) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		
		com.yimayhd.snscenter.client.result.BaseResult<SnsActivityDO> result =activityService.save(activityVO);
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
		com.yimayhd.snscenter.client.result.BaseResult<SnsActivityDO> activity = activityService.getById(id);
		com.yimayhd.snscenter.client.result.BaseResult<List<ClubInfoDO>> clubList = snsCenterService.selectAllClubList(null);
		BaseResult<List<ComTagDO>> tagResultCheck =activityService.getTagInfoByOutIdAndType(id,TagType.ACTIVETYTAG.name() );
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.ACTIVETYTAG.name());
		SnsActivityDO activityDO = activity.getValue();
		
		if(tagResult!=null){
    		model.addAttribute("tagResult",tagResult.getValue());
    	}
    	if(tagResult!=null){
    		model.addAttribute("tagResultCheck",tagResultCheck.getValue());
    	}
		
		List<ActivityJsonDO> list = JSON.parseArray(activityDO.getActiveJson(), ActivityJsonDO.class);
		activityDO.setActivityJsonArray(list);
		ItemResultVO itemResultVO = commodityService.getCommodityById(activityDO.getProductId());
		if(null != itemResultVO&&itemResultVO.isSuccess()){
			model.addAttribute("itemName", itemResultVO.getItemVO().getTitle());
		}
		model.addAttribute("itemType",ItemType.ACTIVITY.getValue());
		model.addAttribute("clubList",clubList.getValue());
		model.addAttribute("activity", activityDO);
		
		return "/system/activity/edit";
	}
	
	/**
	 * enableStatus
	 * 上下架
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateState/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateState(@PathVariable("id") long id,int state) throws Exception {
		Long[] ids={id};
		activityService.updateActivityStateByIList(ids, state);
		return new ResponseVo();
	}
	



}
