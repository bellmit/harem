package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.harem.model.ActivityVO;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
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
    BaseResult<SnsActivityDO> getById(long id)throws Exception;
    BaseResult<SnsActivityDO> save(ActivityVO activityVO);

    
    /**
     * 上下架
     */
    public BaseResult<Boolean> updateActivityStateByIList(Long[] id, int state);
	public com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> getTagInfoByOutIdAndType(long outId,String outType);

}
