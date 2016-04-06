package com.yimayhd.palace.error;

import java.io.Serializable;

/**
 * 取值[25000000 , 26000000)
 * 
 * @author wzf
 *
 */
public class PalaceReturnCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMsg;

	public PalaceReturnCode(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	/******************************************* 系统相关 ******************************************************/
	public static final PalaceReturnCode REMOTE_CALL_FAILED = new PalaceReturnCode(25000000, "远程调用失败!");
	public static final PalaceReturnCode SYSTEM_ERROR = new PalaceReturnCode(25000001, "系统错误!");
	public static final PalaceReturnCode PARAM_ERROR = new PalaceReturnCode(25000002, "参数错误!");
	public static final PalaceReturnCode DATA_NOT_FOUND = new PalaceReturnCode(25000002, "数据不存在");
	
	
	/******************************************* 入驻审批 ******************************************************/
	public static final PalaceReturnCode APPROVE_FAILED = new PalaceReturnCode(25001000, "审批失败");
	public static final PalaceReturnCode APPROVE_REJECT_REASON_EMPTY = new PalaceReturnCode(25001001, "审批不通过需要有原因");

	/***************************************** 商户相关 ********************************************************/
}
