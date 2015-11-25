package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.domain.item.ItemDO;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public interface CommodityService {
    /**
     * 获取商品列表
     * @return
     * @throws Exception
     */
    List<ItemDO> getList()throws Exception;

    /**
     * 根据id获取酒店商品
     * @param id
     * @return
     * @throws Exception
     */
    ItemDO getCommHotelById(long id)throws Exception;

    /**
     * 新增酒店商品
     * @param itemDO
     * @return
     * @throws Exception
     */
    ItemDO addCommHotel(ItemDO itemDO)throws Exception;

    /**
     * 修改酒店商品
     * @param itemDO
     * @throws Exception
     */
    void modifyCommHotel(ItemDO itemDO)throws Exception;

}
