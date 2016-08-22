package com.yimayhd.palace.enums;

import com.yimayhd.ic.client.model.enums.OrderNumFilterEnum;

/**
 *
 */
public enum OrderNumFilter {
	ORDERNUM_EQUALS_ZERO(OrderNumFilterEnum.ORDERNUM_EQUALS_ZERO,"权重为0"),
	ORDERNUM_GT_ZERO(OrderNumFilterEnum.ORDERNUM_GT_ZERO,"权重大于0");
	private String		text;
	private OrderNumFilterEnum orderNumFilterEnum;

	private OrderNumFilter(OrderNumFilterEnum orderNumFilterEnum, String text) {
		this.orderNumFilterEnum = orderNumFilterEnum;
		this.text = text;
	}

	public String getName() {
		return orderNumFilterEnum.name();
	}

	public String getText() {
		return text;
	}

	public static OrderNumFilter getByName(String name) {
		if (name == null) {
			return null;
		}
		for (OrderNumFilter userType : values()) {
			if (userType.name().equals(name)) {
				return userType;
			}
		}
		return null;
	}
}
