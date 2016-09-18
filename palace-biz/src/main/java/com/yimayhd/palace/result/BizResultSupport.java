package com.yimayhd.palace.result;

import java.io.Serializable;

import com.yimayhd.palace.error.BizErrorCode;
import com.yimayhd.palace.error.PalaceReturnCode;

/**
 * 业务支持
 * 
 * @author yebin
 *
 */
public class BizResultSupport implements Serializable {
	private static final long serialVersionUID = -2235152751651905167L;
	protected static final int SUCCESS_CODE = 200;
	private boolean success = true;
	private int code;
	private String msg;
	private PalaceReturnCode palaceReturnCode ;

	public BizResultSupport() {
	}

	public void init(boolean success, int code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public void initFailure(BizErrorCode errorCode) {
		this.success = false;
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}
	
	

	public PalaceReturnCode getPalaceReturnCode() {
		return palaceReturnCode;
	}

	public void setPalaceReturnCode(PalaceReturnCode palaceReturnCode) {
		this.palaceReturnCode = palaceReturnCode;
		if( palaceReturnCode != null ){
			this.success = false;
			this.code = palaceReturnCode.getErrorCode() ;
			this.msg = palaceReturnCode.getErrorMsg() ;
		}
	}

	public void initSuccess(String msg) {
		this.success = true;
		this.code = SUCCESS_CODE;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
