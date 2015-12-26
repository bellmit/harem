package com.yimayhd.harem.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.TfsService;
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

    @Override
    public BasePageResult<SnsActivityDO> getList(ActivityQueryDTO activityQueryDTO) throws Exception {
		//查询主题接口
    	//com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> tagInfoByOutIdAndType= comCenterService.getTagInfoByOutIdAndType(1, "1");
    	return snsCenterService.getActivityPage(activityQueryDTO);
    }
   

	@Override
	public Activity getById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BaseResult<SnsActivityDO> save(ActivityInfoDTO activityInfoDTO,Long[] tagList) {
		activityInfoDTO.setContent(tfsService.publishHtml5(activityInfoDTO.getContent()));
		activityInfoDTO.setMemberCount(10);
		activityInfoDTO.setOriginalPrice(activityInfoDTO.getOriginalPrice()*100);
		activityInfoDTO.setPreferentialPrice(activityInfoDTO.getPreferentialPrice()*100);
		BaseResult<SnsActivityDO> result = snsCenterService.addActivityInfo(activityInfoDTO);
		
		//comCenterService.addTagRelationInfo(tagRelationInfoDTO)
		if (!result.isSuccess()) {
			log.error("snsCenterService.save return value is null !returnValue :"
					+ JSON.toJSONString(result));
			throw new NoticeException(result.getResultMsg());
		}else{
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setList(Arrays.asList(tagList));
			tagRelationInfoDTO.setOutId(result.getValue().getId());
			tagRelationInfoDTO.setTagType(TagType.ACTIVETYTAG.getType());
			tagRelationInfoDTO.setOrderTime(result.getValue().getActivityDate());
			com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagRelationInfoRurult = comCenterService.addTagRelationInfo(tagRelationInfoDTO);
			if (!addTagRelationInfoRurult.isSuccess()){
				log.error("保存活动主题失败：" + addTagRelationInfoRurult.getResultMsg());
				throw new BaseException("保存活动主题失败");
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
