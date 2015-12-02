package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Override
    public List<ItemDO> getList() throws Exception {
        List<ItemDO> itemDOList = new ArrayList<ItemDO>();
        for (int i = 0; i < 10; i++) {
            ItemDO itemDOData = new ItemDO();
            itemDOData.setId(i);
            itemDOData.setRootCategoryId(1);
            itemDOData.setCategoryId(2);
            //酒店id
            //排序
            //时间区间
            if(i % 3 == 0){
                itemDOData.setTitle("故宫门票");
                itemDOData.setSubTitle("通票");
                itemDOData.setPrice(10000);
            }else{
                itemDOData.setTitle("高级双床房");
                itemDOData.setSubTitle("房间35m，双床，可住2人");
                itemDOData.setPrice(9900);
            }
            //会员限购
            itemDOData.setPicUrls("beautiful.png");
            itemDOData.setGmtCreated(new Date());
            itemDOData.setGmtModified(new Date());
            itemDOData.setStatus(1);
            itemDOData.setStockNum(i * 50);
            itemDOList.add(itemDOData);
        }
        return itemDOList;
    }

    @Override
    public ItemResult getCommodityById(long id) throws Exception {
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        //TODO 暂时全部设置成true
        itemOptionDTO.setCreditFade(true);
        itemOptionDTO.setNeedCategory(true);
        itemOptionDTO.setNeedSku(true);
        ItemResult itemResult = itemQueryServiceRef.getItem(id,itemOptionDTO);

        return itemResult;
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
        itemDOData.setGmtCreated(new Date());
        itemDOData.setGmtModified(new Date());

        return itemDOData;
    }

    @Override
    public ItemDO addCommHotel(ItemDO itemDO) throws Exception {
        return null;
    }

    @Override
    public void modifyCommHotel(ItemDO itemDO) throws Exception {

    }

    @Override
    public void setCommStatusList(List<Long> idList, int status) throws Exception {

    }

    @Override
    public void setCommStatus(long id, int status) throws Exception {

    }
}
