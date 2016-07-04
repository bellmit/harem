package com.yimayhd.palace.enums;

/**
 * 九休商品类型
 * 
 * @author yebin
 *
 */
public enum SockpuppetStatusEnum {
	EFFECTIVE(10, "有效"), INVALID(20, "停用");

	private int value;

	private String text;

	/**
	 *
	 * @param value
	 * @param text
	 */
	SockpuppetStatusEnum(final int value, final String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public boolean isEqual(int value) {
		return this.value == value;
	}

	/**
	 *
	 * @param
	 * @return
	 */
	public static SockpuppetStatusEnum get(int enumValue) {
		for (SockpuppetStatusEnum em : SockpuppetStatusEnum.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		throw new IllegalArgumentException("Can't get enum with this enumValue.");
	}

	public static SockpuppetStatusEnum getByName(String name) {
		if (name == null) {
			return null;
		}
		for (SockpuppetStatusEnum userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}

}
