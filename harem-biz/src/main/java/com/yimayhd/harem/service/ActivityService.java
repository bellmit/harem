package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ActivityService {
    /**
     * 获取活动列表(可带查询条件)
     * @return 活动列表
     */
    BasePageResult<SnsActivityDO> getList(ActivityQueryDTO activityQueryDTO)throws Exception;
    /**
     * 获取活动详情
     * @return 活动详情
     */
    Activity getById(long id)throws Exception;
    BaseResult<SnsActivityDO> save(ActivityInfoDTO activityInfoDTO,Long[] tagList);


}
