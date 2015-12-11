package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Trend;

import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public interface TrendService {
    /**
     * 获取动态列表(可带查询条件)
     * @return 动态列表
     */
    List<Trend> getList(Trend trend)throws Exception;
    /**
     * 获取动态详情
     * @return 动态详情
     */
    Trend getById(long id)throws Exception;

    /**
     * 添加动态
     * @param trend
     * @return
     * @throws Exception
     */
    Trend add(Trend trend)throws Exception;

    /**
     * 修改
     * @param trend
     * @throws Exception
     */
    void modify(Trend trend)throws Exception;

    /**
     * 批量修改动态状态
     * @param trendStatusList
     * @throws Exception
     */
    void setTrendStatus(List<Long> trendStatusList)throws Exception;


}
