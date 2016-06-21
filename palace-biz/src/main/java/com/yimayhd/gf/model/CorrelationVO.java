package com.yimayhd.gf.model;

import java.io.Serializable;
import java.util.List;

public class CorrelationVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2675236133233189054L;
	private long masterId;
	private List<Long> recommendIds;
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public List<Long> getRecommendIds() {
		return recommendIds;
	}
	public void setRecommendIds(List<Long> recommendIds) {
		this.recommendIds = recommendIds;
	}
	
	
}
