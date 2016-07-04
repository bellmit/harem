package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * 运营小号
 * 
 * @author xmn
 *
 */
public class SockpuppetListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -603714674325753343L;
	private int status;// 状态
	private String mobile;// 用户手机号
	private String nickname;// 用户昵称
	private int pageNo = 1;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
