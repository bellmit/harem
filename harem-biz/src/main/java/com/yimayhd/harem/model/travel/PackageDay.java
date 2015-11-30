package com.yimayhd.harem.model.travel;

import java.util.Date;
import java.util.Map;

/**
 * 套餐-日
 * 
 * @author yebin
 *
 */
public class PackageDay {
	private long time;// 日期
	private Map<String, String> adult;// 成人
	private Map<String, String> children02;// 儿童0-2
	private Map<String, String> children212;// 儿童2-12
	private String srd;// 单房差

	public Map<String, String> getAdult() {
		return adult;
	}

	public void setAdult(Map<String, String> adult) {
		this.adult = adult;
	}

	public Map<String, String> getChildren02() {
		return children02;
	}

	public void setChildren02(Map<String, String> children02) {
		this.children02 = children02;
	}

	public Map<String, String> getChildren212() {
		return children212;
	}

	public void setChildren212(Map<String, String> children212) {
		this.children212 = children212;
	}

	public String getSrd() {
		return srd;
	}

	public void setSrd(String srd) {
		this.srd = srd;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
