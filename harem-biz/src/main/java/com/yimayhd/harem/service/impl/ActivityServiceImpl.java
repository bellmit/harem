package com.yimayhd.harem.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.TfsService;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
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

    @Override
    public List<SnsActivityDO> getList(ActivityInfoDTO dto) throws Exception {
    	snsCenterService.getActivityPage(dto);
        return null;
    }


	@Override
	public Activity getById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BaseResult<SnsActivityDO> save(ActivityInfoDTO activityInfoDTO) {
		activityInfoDTO.setContent(tfsService.publishHtml5(activityInfoDTO.getContent()));
		activityInfoDTO.setMemberCount(10);
		BaseResult<SnsActivityDO> result = snsCenterService.addActivityInfo(activityInfoDTO);
		if (!result.isSuccess()) {
			log.error("snsCenterService.save return value is null !returnValue :"
					+ JSON.toJSONString(result));
			throw new NoticeException(result.getResultMsg());
		}
		return result;
		
		
	}

  
  


	
}
