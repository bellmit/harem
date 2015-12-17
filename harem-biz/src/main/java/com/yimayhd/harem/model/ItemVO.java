package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ItemVO extends ItemDO {

    private String itemSkuDOStr;

    public static ItemDO getItemDO(ItemVO itemVO){
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO,itemDO);
        return itemDO;
    }

    public List<ItemSkuDO> getItemSkuDOList(){
        List<ItemSkuDO> itemSkuDOList = JSON.parseArray(this.itemSkuDOStr,ItemSkuDO.class);
        return itemSkuDOList;
    }
    public String getItemSkuDOStr() {
        return itemSkuDOStr;
    }

    public void setItemSkuDOStr(String itemSkuDOStr) {
        this.itemSkuDOStr = itemSkuDOStr;
    }
}
