package com.yimayhd.palace.checker.result;

public class CheckResult {
	public static final CheckResult SUCCESS = new CheckResult(true, "验证成功");
	public static final CheckResult FAILURE = new CheckResult(false, "验证失败");
	private boolean success;
	private String msg;

	public CheckResult(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}
}
