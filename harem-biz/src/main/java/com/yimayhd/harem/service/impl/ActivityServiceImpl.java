package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.ActivityVO;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.TfsService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.snscenter.client.domain.ActivityJsonDO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ActivityServiceImpl implements ActivityService {

	private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
    @Autowired
    private SnsCenterService snsCenterService;
    @Autowired
	private TfsService tfsService;
    @Autowired
    private ComCenterService comCenterService;
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Override
    public BasePageResult<SnsActivityDO> getList(ActivityQueryDTO activityQueryDTO) throws Exception {
    	return snsCenterService.getActivityPage(activityQueryDTO);
    }
   

	@Override
	public BaseResult<SnsActivityDO> getById(long id) throws Exception {
		
		BaseResult<SnsActivityDO> activityInfo = snsCenterService.getActivityInfoByActivityId(id);
		if(null == activityInfo){
			log.error("ActivityServiceImpl.getById-snsCenterService.getActivityInfoByActivityId result is null and parame: " + id);
			throw new BaseException("查询结果为空,修改失败 ");
		} else if(!activityInfo.isSuccess()){
			log.error("ActivityServiceImpl.getById--snsCenterService.getActivityInfoByActivityId  error:" + JSON.toJSONString(activityInfo) + "and parame: " +  id);
			throw new BaseException(activityInfo.getResultMsg());
		}
		return activityInfo;
	}
	@Override
	public com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> getTagInfoByOutIdAndType(long outId,String outType){
		com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> talList = comCenterService.getTagInfoByOutIdAndType(outId, outType);
		if(null == talList){
			log.error("ActivityServiceImpl.getById-comCenterService.getTagInfoByOutIdAndType result is null and parame:outId= " + outId + "|outType=" + outType);
			throw new BaseException("查询结果为空,修改失败 ");
		} else if(!talList.isSuccess()){
			log.error("ActivityServiceImpl.getById--comCenterService.getTagInfoByOutIdAndType  error:" + JSON.toJSONString(talList) + "and parame:outId=  " +  + outId + "|outType=" + outType);
			throw new BaseException(talList.getResultMsg());
		}
		return talList;
	}

	@Override
	public BaseResult<SnsActivityDO> save(ActivityVO activityVO) {
		BaseResult<SnsActivityDO> result = null;
	
		if(activityVO.getId()!=0){
			BaseResult<SnsActivityDO> activityInfo = snsCenterService.getActivityInfoByActivityId(activityVO.getId());
			if(null == activityInfo){
				log.error("ActivityServiceImpl.save-snsCenterService.getActivityInfoByActivityId result is null and parame: " + activityVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			} else if(!activityInfo.isSuccess()){
				log.error("ActivityServiceImpl.save--snsCenterService.getActivityInfoByActivityId  error:" + JSON.toJSONString(activityInfo) + "and parame: " +  activityVO.getId());
				throw new BaseException(activityInfo.getResultMsg());
			}
			 SnsActivityDO snsActivityDO = activityInfo.getValue();
			if(snsActivityDO == null){
				log.error("ActivityServiceImpl.save-snsCenterService.getActivityInfoByActivityId result.getValue is null and parame: " + activityVO.getId());
			}
			ActivityInfoDTO dto =ActivityVO.getActivityInfoDTO(activityVO);
			dto.setOriginalPrice(dto.getOriginalPrice());
			dto.setPreferentialPrice(dto.getPreferentialPrice());
			if(org.apache.commons.lang.StringUtils.isNotBlank(dto.getContent())) {
				dto.setContent(tfsService.publishHtml5(dto.getContent()));
			}
			//设置库存
			ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
	        //全部设置成true
	        itemOptionDTO.setCreditFade(true);
	        itemOptionDTO.setNeedCategory(true);
	        itemOptionDTO.setNeedSku(true);
	        ItemResult itemResult = itemQueryServiceRef.getItem(activityVO.getProductId(),itemOptionDTO);
	        if(null == itemResult){
	            log.error("ActivityServiceImpl.save--itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
	            throw new BaseException("返回结果错误");
	        }else if(!itemResult.isSuccess()){
	            log.error("ActivityServiceImpl.save--itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
	            throw new NoticeException(itemResult.getResultMsg());
	        }
			dto.setMemberCount(itemResult.getItem().getStockNum());
			List<ActivityJsonDO> jsonDOs = activityVO.getActivityJsonArray();
			List<ActivityJsonDO> newjsonDOs = new ArrayList<ActivityJsonDO>();
			if(jsonDOs!=null&&!jsonDOs.isEmpty()){
				for (int i = 0; i < jsonDOs.size(); i++) {
					if(StringUtils.isNotBlank(jsonDOs.get(i).getActivityTitle())||StringUtils.isNotBlank(jsonDOs.get(i).getActivityDec())){
						newjsonDOs.add(jsonDOs.get(i));
					}
				}
			}
			dto.setActivityJsonArray(newjsonDOs);
			result = snsCenterService.updateActivityInfo(dto);
			if (!result.isSuccess()) {
				log.error("Activity|snsCenterService.save return value is null !returnValue :"
						+ JSON.toJSONString(result));
				throw new NoticeException(result.getResultMsg());
			}else{
				TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
				tagRelationInfoDTO.setList(Arrays.asList(activityVO.getTagList()));
				tagRelationInfoDTO.setOutId(result.getValue().getId());
				tagRelationInfoDTO.setTagType(TagType.ACTIVETYTAG.getType());
				tagRelationInfoDTO.setOrderTime(result.getValue().getActivityDate());
				com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagRelationInfoRurult = comCenterService.addTagRelationInfo(tagRelationInfoDTO);
				if (!addTagRelationInfoRurult.isSuccess()){
					log.error("保存活动主题失败：" + addTagRelationInfoRurult.getResultMsg());
					throw new BaseException("保存活动主题失败");
				}

			}
		}else{
			ActivityInfoDTO dto =ActivityVO.getActivityInfoDTO(activityVO);
			dto.setOriginalPrice(dto.getOriginalPrice());
			dto.setPreferentialPrice(dto.getPreferentialPrice());
			if(org.apache.commons.lang.StringUtils.isNotBlank(dto.getContent())) {
				dto.setContent(tfsService.publishHtml5(dto.getContent()));
			}
			//设置库存
			ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
	        //全部设置成true
	        itemOptionDTO.setCreditFade(true);
	        itemOptionDTO.setNeedCategory(true);
	        itemOptionDTO.setNeedSku(true);
	        ItemResult itemResult = itemQueryServiceRef.getItem(activityVO.getProductId(),itemOptionDTO);
	        if(null == itemResult){
	            log.error("ActivityServiceImpl.save--itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
	            throw new BaseException("返回结果错误");
	        }else if(!itemResult.isSuccess()){
	            log.error("ActivityServiceImpl.save--itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + activityVO.getProductId());
	            throw new NoticeException(itemResult.getResultMsg());
	        }
			dto.setMemberCount(itemResult.getItem().getStockNum());
			List<ActivityJsonDO> jsonDOs = activityVO.getActivityJsonArray();
			List<ActivityJsonDO> newjsonDOs = new ArrayList<ActivityJsonDO>();
			if(jsonDOs!=null&&!jsonDOs.isEmpty()){
				for (int i = 0; i < jsonDOs.size(); i++) {
					if(StringUtils.isNotBlank(jsonDOs.get(i).getActivityTitle())||StringUtils.isNotBlank(jsonDOs.get(i).getActivityDec())){
						newjsonDOs.add(jsonDOs.get(i));
					}
				}
			}
			dto.setActivityJsonArray(newjsonDOs);
			result = snsCenterService.addActivityInfo(dto);
			if (!result.isSuccess()) {
				log.error("Activity|snsCenterService.save return value is null !returnValue :"
						+ JSON.toJSONString(result));
				throw new NoticeException(result.getResultMsg());
			}else{
				TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
				tagRelationInfoDTO.setList(Arrays.asList(activityVO.getTagList()));
				tagRelationInfoDTO.setOutId(result.getValue().getId());
				tagRelationInfoDTO.setTagType(TagType.ACTIVETYTAG.getType());
				tagRelationInfoDTO.setOrderTime(result.getValue().getActivityDate());
				com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagRelationInfoRurult = comCenterService.addTagRelationInfo(tagRelationInfoDTO);
				if (!addTagRelationInfoRurult.isSuccess()){
					log.error("保存活动主题失败：" + addTagRelationInfoRurult.getResultMsg());
					throw new BaseException("保存活动主题失败");
				}

			}
		}
		return result;
		
		
	}


	@Override
	public BaseResult<Boolean> updateActivityStateByIList(Long[] ids, int state) {
		ActivityQueryDTO dto = new ActivityQueryDTO();
		dto.setActivityIdList(Arrays.asList(ids));
		dto.setState(state);
		BaseResult<Boolean> result = snsCenterService.updateActivityStateByIList(dto);
		return result;
	}

  
	


	
}
