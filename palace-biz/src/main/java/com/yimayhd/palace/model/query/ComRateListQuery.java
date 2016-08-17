package com.yimayhd.palace.model.query;

import com.yimayhd.commentcenter.client.dto.RatePageListDTO;
import com.yimayhd.fhtd.validator.annot.NumberValidator;
import com.yimayhd.palace.base.BaseQuery;

import java.util.List;

public class ComRateListQuery extends BaseQuery {

    private String itemId;
    private String baseStatus = "";
    private String rateStatus = "";
    private String beginDate;
    private String endDate;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBaseStatus() {
        return this.baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getRateStatus() {
        return this.rateStatus;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
