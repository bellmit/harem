package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;


/**
 */
public class ActivityListQuery extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;//名称
    private String activityBeginDate;//活动开始时间
    private String activityEndDate;//活动结束时间

    private Integer satatus;//状态
    private Long productId;//俱乐部
    private Integer status;//是否显示状态
	private String createStartTime;//活动创建时间开始时间
	private String createEndTime;//活动创建时间结束时间

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getSatatus() {
		return satatus;
	}
	public void setSatatus(Integer satatus) {
		this.satatus = satatus;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
}

