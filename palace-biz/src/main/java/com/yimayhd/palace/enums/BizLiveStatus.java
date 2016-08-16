package com.yimayhd.palace.enums;

import com.yimayhd.snscenter.client.enums.BaseStatus;

/**
 * 评论类型
 * 
 * @author xiemingna
 *
 */
public enum BizLiveStatus {
	AVAILABLE(BaseStatus.AVAILABLE, "正常"), DELETED(BaseStatus.DELETED, "违规"), UNAVAILABLE(BaseStatus.UNAVAILABLE, "用户已删除");
	private String text;
	private BaseStatus baseStatus;

	private BizLiveStatus(BaseStatus baseStatus) {
		this.baseStatus = baseStatus;
		this.text = baseStatus.getDesc();
	}

	private BizLiveStatus(BaseStatus baseStatus, String text) {
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
	public static BizLiveStatus get(int enumValue) {
		for (BizLiveStatus em : BizLiveStatus.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizLiveStatus getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizLiveStatus commentType : values()) {
			if (commentType.name().equals(name)) {
				return commentType;
			}
		}
		return null;
	}
}
