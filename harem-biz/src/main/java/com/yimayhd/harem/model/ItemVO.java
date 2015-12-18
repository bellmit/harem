package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ItemVO extends ItemDO {

    private String itemSkuVOStr;//sku列表 jsonString

    private double priceY;//价格（单位:元）

    private List<ItemSkuVO> itemSkuVOList;

    public static ItemDO getItemDO(ItemVO itemVO){
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO, itemDO);
        //元转分
        itemDO.setPrice((long) (itemVO.getPriceY() * 100));
        List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
        for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
            itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO,itemSkuVO));
        }
        itemVO.setItemSkuDOList(itemSkuDOList);
        itemVO.setItemProperties(itemVO.getItemProperties());
        return itemDO;
    }

    public List<ItemSkuVO> getItemSkuVOListByStr(){
        List<ItemSkuVO> itemSkuVOList = JSON.parseArray(this.itemSkuVOStr,ItemSkuVO.class);
        return itemSkuVOList;
    }

    public String getItemSkuVOStr() {
        return itemSkuVOStr;
    }

    public void setItemSkuVOStr(String itemSkuVOStr) {
        this.itemSkuVOStr = itemSkuVOStr;
    }

    public List<ItemSkuVO> getItemSkuVOList() {
        return itemSkuVOList;
    }

    public void setItemSkuVOList(List<ItemSkuVO> itemSkuVOList) {
        this.itemSkuVOList = itemSkuVOList;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }
}
