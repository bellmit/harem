package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.Live;

/**
 * Created by Administrator on 2015/11/16.
 */
public interface LiveService {
    /**
     * 获取直播列表(可带查询条件)
     * @return 直播列表
     */
    List<Live> getList(Live liveListQuery)throws Exception;
    /**
     * 获取直播详情
     * @return 直播详情
     */
    Live getById(long id)throws Exception;

}
