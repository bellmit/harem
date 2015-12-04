package com.yimayhd.harem.base;

import com.alibaba.fastjson.JSON;

/**
 * @author
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = 715232087424762931L;

	public BaseException(Object msg) {
		super(JSON.toJSONString(msg));
	}

	public BaseException(Object msg, Throwable cause) {
		super(JSON.toJSONString(msg), cause);
	}
}
