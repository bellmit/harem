package com.yimayhd.harem.service;

import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.ItemVO;
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
     * 根据id获取商品信息
     * @param id
     * @return
     * @throws Exception
     */
    ItemResultVO getCommodityById(long id)throws Exception;

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


    /**
     * 批量修改商品状态
     * @param idList 商品idList
     * @param status 状态
     * @throws Exception
     */
    void setCommStatusList(List<Long> idList,int status)throws Exception;
    /**
     * 修改商品状态
     * @param id 商品ID
     * @param status 状态
     * @throws Exception
     */
    void setCommStatus(long id,int status)throws Exception;

    /**
     * 新增普通商品
     * @param itemVO 普通商品表单对象
     * @throws Exception
     */
    void addCommonItem(ItemVO itemVO)throws Exception;

    /**
     * 修改普通商品
     * @param itemVO 普通商品表单对象
     * @throws Exception
     */
    void modifyCommonItem(ItemVO itemVO)throws Exception;

}
