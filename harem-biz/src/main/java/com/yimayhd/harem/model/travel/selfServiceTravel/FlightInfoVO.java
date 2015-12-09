package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.Date;

import com.yimayhd.ic.client.model.domain.share_json.FlightInfo;

/**
 * 航班信息
 * 
 * @author yebin
 *
 */
public class FlightInfoVO {
	// 去
	private String goOff;
	private String goFromCity;
	private String goToCity;
	private Date goPlanStart;
	private Date goPlanEnd;
	// 回
	private String backOff;
	private String backFromCity;
	private String backToCity;
	private Date backPlanStart;
	private Date backPlanEnd;

	private String noteInfo;

	public FlightInfoVO() {
	}

	public FlightInfoVO(FlightInfo info) {
		this.goFromCity = info.getForwardDepartCity();
		this.goToCity = info.getForwardArriveCity();
		this.backFromCity = info.getReturnDepartCity();
		this.backToCity = info.getReturnArriveCity();
		this.goPlanStart = new Date(info.getForwardDepartTime());
		this.goPlanEnd = new Date(info.getForwardArriveTime());
		this.backPlanStart = new Date(info.getReturnDepartTime());
		this.backPlanEnd = new Date(info.getReturnArriveTime());
		this.goOff = info.getForwardDate();
		this.backOff = info.getReturnDate();
		this.noteInfo = info.getMemo();
	}

	public String getGoOff() {
		return goOff;
	}

	public void setGoOff(String goOff) {
		this.goOff = goOff;
	}

	public String getGoFromCity() {
		return goFromCity;
	}

	public void setGoFromCity(String goFromCity) {
		this.goFromCity = goFromCity;
	}

	public String getGoToCity() {
		return goToCity;
	}

	public void setGoToCity(String goToCity) {
		this.goToCity = goToCity;
	}

	public Date getGoPlanStart() {
		return goPlanStart;
	}

	public void setGoPlanStart(Date goPlanStart) {
		this.goPlanStart = goPlanStart;
	}

	public Date getGoPlanEnd() {
		return goPlanEnd;
	}

	public void setGoPlanEnd(Date goPlanEnd) {
		this.goPlanEnd = goPlanEnd;
	}

	public String getBackOff() {
		return backOff;
	}

	public void setBackOff(String backOff) {
		this.backOff = backOff;
	}

	public String getBackFromCity() {
		return backFromCity;
	}

	public void setBackFromCity(String backFromCity) {
		this.backFromCity = backFromCity;
	}

	public String getBackToCity() {
		return backToCity;
	}

	public void setBackToCity(String backToCity) {
		this.backToCity = backToCity;
	}

	public Date getBackPlanStart() {
		return backPlanStart;
	}

	public void setBackPlanStart(Date backPlanStart) {
		this.backPlanStart = backPlanStart;
	}

	public Date getBackPlanEnd() {
		return backPlanEnd;
	}

	public void setBackPlanEnd(Date backPlanEnd) {
		this.backPlanEnd = backPlanEnd;
	}

	public String getNoteInfo() {
		return noteInfo;
	}

	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}
}
