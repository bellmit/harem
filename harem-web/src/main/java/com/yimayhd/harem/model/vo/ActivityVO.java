package com.yimayhd.harem.model.vo;

import java.util.List;

import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.query.ActivityListQuery;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ActivityVO {
    private Activity activity;
    private ActivityListQuery activityListQuery;
    private List<Activity> activityList;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityListQuery getActivityListQuery() {
        return activityListQuery;
    }

    public void setActivityListQuery(ActivityListQuery activityListQuery) {
        this.activityListQuery = activityListQuery;
    }
}
