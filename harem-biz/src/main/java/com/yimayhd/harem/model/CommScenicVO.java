package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;

public class CommScenicVO extends ScenicPublishDTO{

	private String pic;
	private long endTime;
	private long startDayTime;
	private long startHourTime;
	private Long[] check;
	private double priceF;
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getStartDayTime() {
		return startDayTime;
	}
	public void setStartDayTime(long startDayTime) {
		this.startDayTime = startDayTime;
	}
	public long getStartHourTime() {
		return startHourTime;
	}
	public void setStartHourTime(long startHourTime) {
		this.startHourTime = startHourTime;
	}
	public Long[] getCheck() {
		return check;
	}
	public void setCheck(Long[] check) {
		this.check = check;
	}
	public double getPriceF() {
		return priceF;
	}
	public void setPriceF(double priceF) {
		this.priceF = priceF;
	}
	
	
	
}
