package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelListQuery extends BaseQuery {
    private String hotelName;//酒店名称
    private int hotelStatus;//状态
    private long regionId;//区域ID
    private String regionName;//区域名称
    private String hotelNameOrTel;//酒店联系电话
    private String BeginDate;//开始创建时间
    private String endDate;//结束创建时间

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelStatus() {
        return hotelStatus;
    }

    public void setHotelStatus(int hotelStatus) {
        this.hotelStatus = hotelStatus;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public String getHotelNameOrTel() {
        return hotelNameOrTel;
    }

    public void setHotelNameOrTel(String hotelNameOrTel) {
        this.hotelNameOrTel = hotelNameOrTel;
    }
}
