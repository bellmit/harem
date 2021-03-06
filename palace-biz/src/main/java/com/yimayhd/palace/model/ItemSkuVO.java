package com.yimayhd.palace.model;

import com.yimayhd.ic.client.model.domain.item.ItemSkuFeature;
import com.yimayhd.ic.client.model.enums.ItemSkuFeatureKey;
import com.yimayhd.palace.model.enums.ItemSkuStatus;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;

/**
 * Created by czf on 2015/12/17.
 */
public class ItemSkuVO extends ItemSkuDO {
    private double priceY;
    private int status = ItemSkuStatus.ADD.getStatus();//默认为新增
    
    private boolean checked = false;//此条sku是否已展示
    private boolean modifyStatus = false;//是否修改过

    private String mainPic;//sku主图
    private String pcMainPic;//PC版sku主图
    
    private String itemSkuCode = ""; //sku编码

	public static ItemSkuDO getItemSkuDO(ItemVO itemVO,ItemSkuVO itemSkuVO){
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        BeanUtils.copyProperties(itemSkuVO, itemSkuDO);
        //个性化转换
        itemSkuDO.setId(itemSkuVO.getId());
        itemSkuDO.setSellerId(itemVO.getSellerId());
        itemSkuDO.setItemId(itemVO.getId());

        for(ItemSkuPVPair itemSkuPVPair:itemSkuVO.getItemSkuPVPairList()){
            if(StringUtils.isBlank(itemSkuDO.getTitle())){
                itemSkuDO.setTitle(itemSkuPVPair.getVTxt());
            }else{
                itemSkuDO.setTitle(itemSkuDO.getTitle() + "," + itemSkuPVPair.getVTxt());
            }
        }
        itemSkuDO.setCategoryId(itemVO.getCategoryId());
        //元转分
        itemSkuDO.setPrice(NumUtil.doubleToLong(itemSkuVO.getPriceY()));
        //sku主图和PC版sku主图
        ItemSkuFeature itemSkuFeature = itemSkuDO.getItemSkuFeature();
        if(itemSkuFeature == null){
            itemSkuFeature = new ItemSkuFeature(null);
            itemSkuDO.setItemSkuFeature(itemSkuFeature);
        }
        if(StringUtils.isNotBlank(itemSkuVO.getMainPic())){
            //TODO 暂时用一个
            itemSkuFeature.put(ItemSkuFeatureKey.MAIN_PIC,itemSkuVO.getMainPic());
            itemSkuFeature.put(ItemSkuFeatureKey.PC_MAIN_PIC,itemSkuVO.getMainPic());
        }
        
        itemSkuFeature.put(ItemSkuFeatureKey.ITEM_SKU_CODE, itemSkuVO.getItemSkuCode());

        return itemSkuDO;
    }

    /**
     *根据itemSkuDO获取itemSkuVO
     * @param itemSkuDO
     * @return
     */
    public static ItemSkuVO getItemSkuVO(ItemSkuDO itemSkuDO){
        ItemSkuVO itemSkuVO = new ItemSkuVO();
        BeanUtils.copyProperties(itemSkuDO, itemSkuVO);
        //分转元
        itemSkuVO.setPriceY(NumUtil.moneyTransformDouble(itemSkuVO.getPrice()));
        //sku主图和PC版sku主图
        ItemSkuFeature itemSkuFeatrue = itemSkuDO.getItemSkuFeature();
        if(itemSkuFeatrue != null) {
            itemSkuVO.setMainPic(itemSkuFeatrue.getMainPic());
            itemSkuVO.setPcMainPic(itemSkuFeatrue.getMainPic());
            itemSkuVO.setItemSkuCode(itemSkuFeatrue.getItemSkuCode());
        }
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

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getPcMainPic() {
        return pcMainPic;
    }

    public void setPcMainPic(String pcMainPic) {
        this.pcMainPic = pcMainPic;
    }
    
	public String getItemSkuCode() {
		return itemSkuCode;
	}

	public void setItemSkuCode(String itemSkuCode) {
		this.itemSkuCode = itemSkuCode;
	}

    public static class ItemSkuVOSort implements Comparator<ItemSkuVO>{
        public int compare(ItemSkuVO o1, ItemSkuVO o2) {
            for (ItemSkuPVPair itemSkuPVPair1 : o1.getItemSkuPVPairList()){
                for (ItemSkuPVPair itemSkuPVPair2 : o2.getItemSkuPVPairList()){
                    if(itemSkuPVPair1.getPId() == itemSkuPVPair2.getPId()){
                        if(itemSkuPVPair1.getVId() != itemSkuPVPair2.getVId()){
                            return itemSkuPVPair1.getVId() < itemSkuPVPair2.getVId() ? -1 : 1;
                        }
                    }
                }
            }
            return 0;
        }
    }
}

