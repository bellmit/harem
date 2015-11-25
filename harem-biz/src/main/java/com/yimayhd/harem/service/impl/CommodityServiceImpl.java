package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CommodityServiceImpl implements CommodityService {
    @Override
    public List<ItemDO> getList() throws Exception {
        return null;
    }

    @Override
    public ItemDO getCommHotelById(long id) throws Exception {
        ItemDO itemDOData = new ItemDO();
        itemDOData.setId(id);
        itemDOData.setRootCategoryId(1);
        itemDOData.setCategoryId(2);
        //酒店id
        //排序
        //时间区间
        itemDOData.setTitle("高级双床房");
        itemDOData.setSubTitle("房间35m，双床，可住2人");
        itemDOData.setPrice(9900);
        //会员限购
        itemDOData.setPicUrls("");

        return itemDOData;
    }

    @Override
    public ItemDO addCommHotel(ItemDO itemDO) throws Exception {
        return null;
    }

    @Override
    public void modifyCommHotel(ItemDO itemDO) throws Exception {

    }
}
