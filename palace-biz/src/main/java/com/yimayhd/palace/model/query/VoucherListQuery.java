package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherListQuery extends BaseQuery{

    private String beginDate;
    private String endDate;
    private int status;
    private int voucherType;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }
}
