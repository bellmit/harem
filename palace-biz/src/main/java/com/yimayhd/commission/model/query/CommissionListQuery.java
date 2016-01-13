package com.yimayhd.commission.model.query;

import com.yimayhd.palace.base.BaseQuery;

public class CommissionListQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405732862120567997L;

	private String userName;
	
	private String telNum;
	
	private long domainId;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}
	
}
