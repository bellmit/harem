package com.yimayhd.harem.model;

import com.yimayhd.harem.model.enums.ItemSkuStatus;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2015/12/17.
 */
public class ItemSkuVO extends ItemSkuDO {
    private double priceY;
    private int status = ItemSkuStatus.ADD.getStatus();//默认为新增
    
    private boolean checked = false;//此条sku是否已展示
    private boolean modifyStatus = false;//是否修改过
    
    public static ItemSkuDO getItemSkuDO(ItemVO itemVO,ItemSkuVO itemSkuVO){
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        BeanUtils.copyProperties(itemSkuVO, itemSkuDO);
        //个性化转换
        itemSkuDO.setId(itemSkuVO.getId());
        itemSkuDO.setSellerId(itemVO.getSellerId());
        itemSkuDO.setItemId(itemVO.getId());

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

    /**
     *根据itemSkuDO获取itemSkuVO
     * @param itemSkuDO
     * @return
     */
    public static ItemSkuVO getItemSkuVO(ItemSkuDO itemSkuDO){
        ItemSkuVO itemSkuVO = new ItemSkuVO();
        BeanUtils.copyProperties(itemSkuDO,itemSkuVO);
        //分转元
        itemSkuVO.setPriceY(itemSkuVO.getPrice() / 100);
        return itemSkuVO;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(boolean modifyStatus) {
        this.modifyStatus = modifyStatus;
    }
}
