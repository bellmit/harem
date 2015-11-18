package com.yimayhd.harem.service;

import com.yimayhd.harem.model.vo.HotelVO;
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
    List<HotelDO> getList(HotelVO hotelVO)throws Exception;
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

    /**
     * 批量修改酒店状态
     * @param idList
     * @param hotelStatus
     * @throws Exception
     */
    void setHotelStatusList(List<Integer> idList,int hotelStatus)throws Exception;
    /**
     * 修改酒店状态
     * @param id
     * @param hotelStatus
     * @throws Exception
     */
    void setHotelStatus(long id,int hotelStatus)throws Exception;

}
