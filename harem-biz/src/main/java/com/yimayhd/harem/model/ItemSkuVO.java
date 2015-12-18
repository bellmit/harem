package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ItemSkuVO extends ItemSkuDO {
    private double priceY;

    public static ItemSkuDO getItemSkuDO(ItemVO itemVO,ItemSkuVO itemSkuVO){
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        BeanUtils.copyProperties(itemSkuVO, itemSkuDO);
        //个性化转换
        itemSkuDO.setId(itemSkuVO.getId());
        itemSkuDO.setSellerId(itemVO.getSellerId());
        itemSkuDO.setItemId(itemVO.getId());
        //没有状态时为新增，设置为可用
        if(0 == itemSkuDO.getStatus()){
            itemSkuDO.setStatus(BaseStatus.AVAILABLE.getType());
        }

        for(ItemSkuPVPair itemSkuPVPair:itemSkuVO.getItemSkuPVPairList()){
            itemSkuPVPair.getVTxt();
            if(StringUtils.isBlank(itemSkuDO.getTitle())){
                itemSkuDO.setTitle(itemSkuDO.getTitle());
            }else{
                itemSkuDO.setTitle("," + itemSkuDO.getTitle());
            }
        }
        itemSkuDO.setCategoryId(itemVO.getCategoryId());
        //元转分
        itemSkuDO.setPrice((long) (itemSkuVO.getPriceY() * 100));


        return itemSkuDO;
    }
    public static ItemVO getItemVO(ItemSkuDO itemSkuDO){
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemSkuDO,itemVO);
        //TODO 个性化转换
        return itemVO;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }
}
