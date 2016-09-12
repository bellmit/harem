package com.yimayhd.gf.model.query;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ReplayQueryVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoyang
 * @date 2016年9月12日
 */
public class ReplayQueryVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -876817201699537127L;

	private String talentName;
	private long talentId;
	private int liveCategoryId;
	private int status;
	private Date beginDate;
	private Date endDate;
	private int sortWay;
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
	public int getLiveCategoryId() {
		return liveCategoryId;
	}
	public void setLiveCategoryId(int liveCategoryId) {
		this.liveCategoryId = liveCategoryId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getSortWay() {
		return sortWay;
	}
	public void setSortWay(int sortWay) {
		this.sortWay = sortWay;
	}
	
}
