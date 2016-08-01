package com.yimayhd.palace.enums;

import com.yimayhd.commentcenter.client.enums.CommentType;

/**
 * 评论类型
 * 
 * @author xiemingna
 *
 */
public enum BizCommentType {
	DYNAMICCOM(CommentType.DYNAMICCOM, "动态");
	private String text;
	private CommentType commentType;

	private BizCommentType(CommentType commentType) {
		this.commentType = commentType;
		this.text = commentType.getDesc();
	}

	private BizCommentType(CommentType commentType, String text) {
		this.commentType = commentType;
		this.text = text;
	}

	public int getValue() {
		return commentType.getType();
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
	public static BizCommentType get(int enumValue) {
		for (BizCommentType em : BizCommentType.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		return null;
	}

	public static BizCommentType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (BizCommentType commentType : values()) {
			if (commentType.name().equals(name)) {
				return commentType;
			}
		}
		return null;
	}
}
