package com.yimayhd.harem.model.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yimayhd.harem.base.BaseQuery;


/**
 */
public class ActivityListQuery extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startTime;

	private String endTime;

	private String activityStartTime;
	
	private String activityEndTime;
	
	private String title;
	
	private int state;
	
	private long clubId;
	
	private List<Long> activityIdList = new ArrayList<Long>();

	

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getClubId() {
		return clubId;
	}

	public void setClubId(long clubId) {
		this.clubId = clubId;
	}

	public List<Long> getActivityIdList() {
		return activityIdList;
	}

	public void setActivityIdList(List<Long> activityIdList) {
		this.activityIdList = activityIdList;
	}

}

