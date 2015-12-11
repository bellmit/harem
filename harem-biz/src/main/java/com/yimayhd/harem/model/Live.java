package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

public class Live extends BaseModel{
	private String name;//用户昵称
	private String phone;//用户手机号
	private String content;//内容
	private String liveTag;//标签
	private Integer likeNum;//点赞数
	private Integer commertNum;//评论数
	private Integer shareNum;//分享数
	
	private String location;//定位
	private String auditLogging;//  审核记录
	private String ipAddress;//IP地址
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLiveTag() {
		return liveTag;
	}
	public void setLiveTag(String liveTag) {
		this.liveTag = liveTag;
	}
	public Integer getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	public Integer getCommertNum() {
		return commertNum;
	}
	public void setCommertNum(Integer commertNum) {
		this.commertNum = commertNum;
	}
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAuditLogging() {
		return auditLogging;
	}
	public void setAuditLogging(String auditLogging) {
		this.auditLogging = auditLogging;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
