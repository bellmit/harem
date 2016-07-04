package com.yimayhd.palace.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 运营小号
 * 
 * @author xmn
 *
 */
public class SockpuppetVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9067731762329013847L;
	private Long id;
	private Long userId;
	private String nickname;
	private Long mobile;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;
	private String statusText;

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Integer getStatus() {
		return status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
