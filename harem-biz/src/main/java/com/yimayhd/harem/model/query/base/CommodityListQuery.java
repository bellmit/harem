package com.yimayhd.harem.model.query.base;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommodityListQuery extends BaseQuery {
    private long category_id;//商品分类
    private String commName;//商品名称
    private String commCode;//商品编码
    private int commStatus;//状态
    private String BeginDate;//发布开始时间
    private String endDate;//发布结束时间

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getCommCode() {
        return commCode;
    }

    public void setCommCode(String commCode) {
        this.commCode = commCode;
    }

    public int getCommStatus() {
        return commStatus;
    }

    public void setCommStatus(int commStatus) {
        this.commStatus = commStatus;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
