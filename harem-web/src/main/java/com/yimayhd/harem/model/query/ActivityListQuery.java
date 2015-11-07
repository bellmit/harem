package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ActivityListQuery extends BaseQuery {
    private String name;//名称
    private String activityBeginDate;//活动开始时间
    private String activityEndDate;//活动结束时间

    private Integer activityStatus;//状态
    private Long club;//俱乐部
    private Integer isStatus;//是否显示状态

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityBeginDate() {
        return activityBeginDate;
    }

    public void setActivityBeginDate(String activityBeginDate) {
        this.activityBeginDate = activityBeginDate;
    }

    public String getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(String activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Long getClub() {
        return club;
    }

    public void setClub(Long club) {
        this.club = club;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }
}

