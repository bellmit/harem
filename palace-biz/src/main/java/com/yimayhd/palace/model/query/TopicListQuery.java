package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

public class TopicListQuery  extends BaseQuery {

	private static final long serialVersionUID = 1L;
	
    private String title;
    
	private String status;

    private String startTime;

	private String endTime;
	
	 /**
     * 是否有描述
     */
    private Boolean hasContent;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
	
	public Boolean getHasContent() {
		return hasContent;
	}

	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}
}
