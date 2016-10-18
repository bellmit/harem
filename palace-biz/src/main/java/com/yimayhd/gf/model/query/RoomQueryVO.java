/**
 * 
 */
package com.yimayhd.gf.model.query;

import java.io.Serializable;

/**
 * @ClassName: RoomQueryVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoyang
 * @date 2016年9月12日
 */
public class RoomQueryVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 8112789013548992836L;
	private String talentName;
	private long talentId;
	private String roomNum;
	private int status;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
