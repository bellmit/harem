package com.yimayhd.palace.checker;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.palace.checker.result.CheckResult;

/**
 * Property checker
 * 
 * @author yebin
 *
 */
public class PropertyChecker {
	public static CheckResult checkProperty(long pId, int pType, String pTxt) {
		if (pId <= 0) {
			return CheckResult.error("无效PropertyID");
		}
		if (pType <= 0) {
			return CheckResult.error("无效Property类型");
		}
		if (StringUtils.isNotBlank(pTxt)) {
			return CheckResult.error("Property名称不能为空");
		}
		return CheckResult.success();
	}
	
	public static CheckResult checkPropertyValue(long pId, int pType, String pTxt) {
		if (pId <= 0) {
			return CheckResult.error("无效PropertyID");
		}
		if (pType <= 0) {
			return CheckResult.error("无效Property类型");
		}
		if (StringUtils.isNotBlank(pTxt)) {
			return CheckResult.error("Property名称不能为空");
		}
		return CheckResult.success();
	}
}
