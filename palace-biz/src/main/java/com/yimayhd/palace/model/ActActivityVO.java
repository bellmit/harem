package com.yimayhd.palace.model;

import com.yimayhd.activitycenter.domain.ActActivityDO;

/**
 * Created by czf on 2016/2/6.
 */
public class ActActivityVO extends ActActivityDO {

    private int entityType;

    private double requirementY;

    private int promotionType;

    private String startDateStr;

    private String endDateStr;

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
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
}
