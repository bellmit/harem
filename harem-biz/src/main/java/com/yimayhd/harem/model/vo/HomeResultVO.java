package com.yimayhd.harem.model.vo;

import java.io.Serializable;
import java.util.List;

public class HomeResultVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3894408522360799520L;

	
	private long boothId;
	
	private String boothCode;
	
	private String boothDesc;
	
	private List<HomeResultInfo> homeCfgInfoList;

	public long getBoothId() {
		return boothId;
	}

	public void setBoothId(long boothId) {
		this.boothId = boothId;
	}

	public String getBoothCode() {
		return boothCode;
	}

	public void setBoothCode(String boothCode) {
		this.boothCode = boothCode;
	}

	public String getBoothDesc() {
		return boothDesc;
	}

	public void setBoothDesc(String boothDesc) {
		this.boothDesc = boothDesc;
	}

	public List<HomeResultInfo> getHomeCfgInfoList() {
		return homeCfgInfoList;
	}

	public void setHomeCfgInfoList(List<HomeResultInfo> homeCfgInfoList) {
		this.homeCfgInfoList = homeCfgInfoList;
	}
	
}
