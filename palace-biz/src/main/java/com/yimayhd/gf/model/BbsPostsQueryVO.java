package com.yimayhd.gf.model;

import com.yimayhd.palace.base.BaseQuery;

public class BbsPostsQueryVO extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6268192784904260515L;
	
	
	private Long moduleId;
	private Integer status;
	private String startTime;
	private String endTime;
	private String title;
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
}
