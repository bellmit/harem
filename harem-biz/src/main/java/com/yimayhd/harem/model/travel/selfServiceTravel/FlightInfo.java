package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.Date;

/**
 * 航班信息
 * 
 * @author yebin
 *
 */
public class FlightInfo {
	private Date off;
	private String fromCity;
	private String fromAirport;
	private String toCity;
	private String toAirport;
	private String airways;
	private String flightNumber;
	private Date planStart;
	private Date planEnd;
	private String runTime;
	private String noteInfo;

	public Date getOff() {
		return off;
	}

	public void setOff(Date off) {
		this.off = off;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(String fromAirport) {
		this.fromAirport = fromAirport;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToAirport() {
		return toAirport;
	}

	public void setToAirport(String toAirport) {
		this.toAirport = toAirport;
	}

	public String getAirways() {
		return airways;
	}

	public void setAirways(String airways) {
		this.airways = airways;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Date getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getNoteInfo() {
		return noteInfo;
	}

	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}
}
