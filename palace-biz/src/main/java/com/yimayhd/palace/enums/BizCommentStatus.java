package com.yimayhd.palace.enums;

import com.yimayhd.commentcenter.client.enums.BaseStatus;

/**
 * 评论类型
 * 
 * @author xiemingna
 *
 */
public enum BizCommentStatus {
	AVAILABLE(BaseStatus.AVAILABLE, "正常"), DELETED(BaseStatus.DELETED, "违规");
	private String text;
	private BaseStatus baseStatus;

	private BizCommentStatus(BaseStatus baseStatus) {
		this.baseStatus = baseStatus;
		this.text = baseStatus.getDesc();
	}

	private BizCommentStatus(BaseStatus baseStatus, String text) {
		this.baseStatus = baseStatus;
		this.text = text;
	}

	public int getValue() {
		return baseStatus.getType();
	}

	public String getText() {
		return text;
	}

	public boolean isEqual(int value) {
		return getValue() == value;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static BizCommentStatus get(int enumValue) {
		for (BizCommentStatus em : BizCommentStatus.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizCommentStatus getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizCommentStatus commentType : values()) {
			if (commentType.name().equals(name)) {
				return commentType;
			}
		}
		return null;
	}
}
