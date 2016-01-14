package com.yimayhd.palace.model.vo;


import com.yimayhd.voucher.client.domain.VoucherTemplateDO;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherTemplateVO extends VoucherTemplateDO {
    private String beginDate;
    private String endDate;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
