package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialListQuery extends BaseQuery {
    private Integer travelStatus;
    private String title;
    private Long regionId;
    private String regionName;
    private String userName;

    public Integer getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(Integer travelStatus) {
        this.travelStatus = travelStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
