package com.yimayhd.harem.model.travel.groupTravel;

import java.util.Map;
import java.util.TreeMap;

/**
 * 交通方式
 * 
 * @author yebin
 *
 */
public class TripTraffic {
	public static final Map<Integer, String> WAYS = new TreeMap<Integer, String>();

	static {
		WAYS.put(0, "飞机");
		WAYS.put(1, "火车");
		WAYS.put(2, "巴士");
		WAYS.put(3, "轮船");
	}

	private String from;
	private String to;
	private int way;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public String wayName() {
		return WAYS.get(way);
	}
}
