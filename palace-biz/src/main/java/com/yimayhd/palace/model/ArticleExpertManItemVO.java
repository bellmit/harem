package com.yimayhd.palace.model;

import java.io.Serializable;

public class ArticleExpertManItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String headPic;
	private String nickName;
	private String signatures;

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSignatures() {
		return signatures;
	}

	public void setSignatures(String signatures) {
		this.signatures = signatures;
	}

}
