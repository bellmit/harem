package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Activity;
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

    
    /**
     * 上下架
     */
    public BaseResult<Boolean> updateActivityStateByIList(Long[] id, int state);

}
