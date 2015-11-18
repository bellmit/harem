package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.domain.HotelDO;

import java.util.List;

/**
 * 酒店资源管理
 */
public interface HotelService {
    /**
     * 获取酒店资源列表(可带查询条件)
     * @return 酒店资源列表
     */
    List<HotelDO> getList(HotelDO hotelDO)throws Exception;
    /**
     * 获取酒店资源详情
     * @return 酒店资源详情
     */
    HotelDO getById(long id)throws Exception;

    /**
     * 添加酒店资源
     * @param hotelDO
     * @return
     * @throws Exception
     */
    HotelDO add(HotelDO hotelDO)throws Exception;

    /**
     * 修改酒店资源
     * @param hotelDO
     * @throws Exception
     */
    void modify(HotelDO hotelDO)throws Exception;

}
