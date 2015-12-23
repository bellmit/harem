package com.yimayhd.harem.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.result.ResultSupport;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.ic.client.model.result.ICErrorCode;
import com.yimayhd.ic.client.model.result.ICResultSupport;

public class LogUtil {
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;

	private static String RESULT_NULL = "{} result is null";
	private static String RESULT_FAILURE = "{} error {}: {}";
	private static String RESULT_SUCCESS = "{} success";

	/**
	 * 处理IC服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void icResultLog(Logger log, String method, ICResultSupport result) {
		String prefix = "ItemCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			String code = "no code";
			String msg = "no message";
			ICErrorCode errorCode = result.getErrorCode();
			if (errorCode != null) {
				code = errorCode.getErrorCode() + "";
				msg = errorCode.getErrorMsg();
			}
			log.error(RESULT_FAILURE, method, code, msg);
			throw new BaseException(prefix + msg);
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * 处理CommentCenter服务返回结果的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void ccResultLog(Logger log, String method, ResultSupport result) {
		String prefix = "CommentCenter服务接口错误：";
		if (result == null) {
			log.error(RESULT_NULL, method);
			throw new BaseException(prefix + "返回结果错误");
		} else if (!result.isSuccess()) {
			log.error(RESULT_FAILURE, method, result.getErrorCode(), result.getResultMsg());
			throw new BaseException(prefix + result.getResultMsg());
		} else {
			log.info(RESULT_SUCCESS, method);
		}
	}

	/**
	 * 请求的log
	 * 
	 * @param log
	 * @param method
	 * @param result
	 */
	public static void requestLog(Logger log, String method, Object... param) {
		log.info("Request {} Param: {}", method, JSON.toJSONString(param));
	}

	/**
	 * 异常日志
	 * 
	 * @param log=
	 * @param e
	 * @param param
	 */
	public static void exceptionLog(Logger log, String msg, Exception e, Object... param) {
		exceptionLog(log, msg, false, e, param);
	}

	public static void exceptionLog(Logger log, String msg, boolean isThrow, Exception e, Object... param) {
		paramLog(log, ERROR, e, param);
		log.error(msg, e);
		if (isThrow) {
			throw new BaseException(msg);
		}
	}

	/**
	 * 必须是key-value形式
	 * 
	 * @param log
	 * @param logLevel
	 * @param param
	 */
	public static void paramLog(Logger log, int logLevel, Object... param) {
		if (param.length % 2 != 0) {
			throw new RuntimeException("LogUtil.exceptionLog 'Param' is key-value pair: " + param);
		}
		int size = param.length / 2;
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		for (int i = 0; i < size; i++) {
			int key = i * 2;
			int value = i * 2 + 1;
			paramMap.put(param[key], JSON.toJSONString(param[value]));
		}
		print(log, logLevel, "Param: {}", paramMap);
	}

	private static void print(Logger log, int logLevel, String format, Object... args) {
		switch (logLevel) {
		case DEBUG:
			log.debug(format, args);
			break;
		case INFO:
			log.info(format, args);
			break;
		case WARN:
			log.warn(format, args);
			break;
		case ERROR:
			log.error(format, args);
			break;
		default:
			log.info(format, args);
		}
	}
}
