package com.yimayhd.palace.model.vo.apply;

import java.io.Serializable;

public class ApproveVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id ;
	
	/**
	 * 原因
	 */
	private String reason ;
	/**
	 * 是否通过
	 */
	private boolean pass;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
