package com.yimayhd.gf.model.query;

import java.io.Serializable;

/**
 * @ClassName: LiveQueryVO
 * @Description: 直播管理查询类
 * @author zhangxiaoyang
 * @date 2016年9月12日
 */
public class LiveQueryVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -7649756512886742582L;

	private String talentName;
	private long talentId;
	private String roomNum;
	private int liveCategoryId;
	public String getTalentName() {
		return talentName;
	}
	public void setTalentName(String talentName) {
		this.talentName = talentName;
	}
	public long getTalentId() {
		return talentId;
	}
	public void setTalentId(long talentId) {
		this.talentId = talentId;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public int getLiveCategoryId() {
		return liveCategoryId;
	}
	public void setLiveCategoryId(int liveCategoryId) {
		this.liveCategoryId = liveCategoryId;
	}
	
}
