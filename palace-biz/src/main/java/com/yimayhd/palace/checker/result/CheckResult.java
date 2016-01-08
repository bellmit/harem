package com.yimayhd.palace.checker.result;

import com.yimayhd.palace.error.BizErrorCode;
import com.yimayhd.palace.result.BizResultSupport;

public class CheckResult extends BizResultSupport {
	private static final long serialVersionUID = -3672364961175610633L;

	public static CheckResult success() {
		CheckResult checkResult = new CheckResult();
		checkResult.initSuccess("验证成功");
		return checkResult;
	}
	public static CheckResult error() {
		CheckResult checkResult = new CheckResult();
		checkResult.initFailure(BizErrorCode.ParametersValidateError);
		return checkResult;
	}
	public static CheckResult error(String msg) {
		CheckResult checkResult = error();
		checkResult.setMsg(msg);
		return checkResult;
	}
}
