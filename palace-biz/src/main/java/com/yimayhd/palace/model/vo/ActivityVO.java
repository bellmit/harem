package com.yimayhd.palace.model.vo;

import com.yimayhd.palace.model.Activity;
import com.yimayhd.palace.model.query.ActivityListQuery;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ActivityVO {
    private Activity activity;
    private ActivityListQuery activityListQuery;

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
