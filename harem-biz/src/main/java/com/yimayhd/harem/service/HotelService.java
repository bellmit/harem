package com.yimayhd.harem.service;

import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
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
    List<HotelDO> getList(HotelListQuery hotelListQuery)throws Exception;
    /**
     * 获取酒店资源详情
     * @return 酒店资源详情
     */
    HotelVO getById(long id)throws Exception;

    /**
     * 添加酒店资源
     * @param hotelVO
     * @return
     * @throws Exception
     */
    HotelDO add(HotelVO hotelVO)throws Exception;

    /**
     * 修改酒店资源
     * @param hotelVO
     * @throws Exception
     */
    void modify(HotelVO hotelVO)throws Exception;



}
