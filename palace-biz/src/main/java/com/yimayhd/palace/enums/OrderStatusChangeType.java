package com.yimayhd.palace.enums;




public enum OrderStatusChangeType {
	FINISH(1, "完成"),
	CANCEL(2, "取消");

	private int value;

	private String text;

	/**
	 *
	 * @param value
	 * @param text
	 */
	OrderStatusChangeType(final int value, final String text) {
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
	public static OrderStatusChangeType get(int enumValue) {
		for (OrderStatusChangeType em : OrderStatusChangeType.values()) {
			if (em.getValue() == enumValue) {
				return em;
			}
		}
		throw new IllegalArgumentException("Can't get enum with this enumValue.");
	}

	public static OrderStatusChangeType getByName(String name) {
		if (name == null) {
			return null;
		}
		for (OrderStatusChangeType userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}

}
