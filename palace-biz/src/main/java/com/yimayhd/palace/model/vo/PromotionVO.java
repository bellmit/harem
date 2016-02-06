package com.yimayhd.palace.model.vo;


import com.yimayhd.promotion.client.domain.PromotionDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/1/11.
 */
public class PromotionVO extends PromotionDO {

    private double valueY;//金额或者折扣
    private boolean isDel = false;
    private boolean isModify = false;

    public static PromotionVO getPromotionVO(PromotionDO promotionDO){
        PromotionVO promotionVO = new PromotionVO();
        BeanUtils.copyProperties(promotionDO,promotionVO);
        promotionVO.setValueY(promotionDO.getValue() / 100);
        return promotionVO;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setIsModify(boolean isModify) {
        this.isModify = isModify;
    }

    public double getValueY() {
        return valueY;
    }

    public void setValueY(double valueY) {
        this.valueY = valueY;
    }
}
