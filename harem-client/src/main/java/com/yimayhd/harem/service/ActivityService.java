package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.Club;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ActivityService {
    /**
     * 获取活动列表(可带查询条件)
     * @return 活动列表
     */
    List<Activity> getList(Activity activity)throws Exception;
    /**
     * 获取活动详情
     * @return 活动详情
     */
    Activity getById(long id)throws Exception;

    /**
     * 添加活动
     * @param activity
     * @return
     * @throws Exception
     */
    Activity add(Activity activity)throws Exception;

    /**
     * 修改活动
     * @param activity
     * @throws Exception
     */
    void modify(Activity activity)throws Exception;

}
