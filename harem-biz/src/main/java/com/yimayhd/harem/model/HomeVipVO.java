package com.yimayhd.harem.model;

import java.io.Serializable;

public class HomeVipVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6285809936992791263L;

	private long[] vipIds;
	
	private String[] vipImgUrl;
	
	private String vipTitle;

	

	public long[] getVipIds() {
		return vipIds;
	}

	public void setVipIds(long[] vipIds) {
		this.vipIds = vipIds;
	}

	public String[] getVipImgUrl() {
		return vipImgUrl;
	}

	public void setVipImgUrl(String[] vipImgUrl) {
		this.vipImgUrl = vipImgUrl;
	}

	public String getVipTitle() {
		return vipTitle;
	}

	public void setVipTitle(String vipTitle) {
		this.vipTitle = vipTitle;
	}
	
}
