package com.yimayhd.harem.model.travel.selfServiceTravel;

/**
 * 往返航班
 * 
 * @author yebin
 *
 */
public class FlightPair {
	private FlightInfo go;
	private FlightInfo back;

	public FlightInfo getGo() {
		return go;
	}

	public void setGo(FlightInfo go) {
		this.go = go;
	}

	public FlightInfo getBack() {
		return back;
	}

	public void setBack(FlightInfo back) {
		this.back = back;
	}

}
