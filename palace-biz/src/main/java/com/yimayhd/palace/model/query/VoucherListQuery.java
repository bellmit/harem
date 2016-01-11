package com.yimayhd.palace.model.query;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherListQuery {
    private String title;//标题
    private String beginDate;//优惠券模板创建开始时间
    private String endDate;//优惠券模板创建结束时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
