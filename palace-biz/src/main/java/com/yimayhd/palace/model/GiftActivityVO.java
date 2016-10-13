package com.yimayhd.palace.model;


import com.yimayhd.promotion.client.domain.PromotionDO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by p on 9/1/16.
 */
public class GiftActivityVO extends PromotionDO {
    private static final long serialVersionUID = -2899232236987017479L;
    private int entityType;// leixing
    private long entityId;//店铺优惠用
    private double requirementY; // fill
    private int promotionType; //
    private String startDateStr; //
    private String endDateStr; //
    private String baseStatus;
    private List<GiftVO> gifts;

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public double getRequirementY() {
        return requirementY;
    }

    public void setRequirementY(double requirementY) {
        this.requirementY = requirementY;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public List<GiftVO> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftVO> gifts) {
        this.gifts = gifts;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }
}
