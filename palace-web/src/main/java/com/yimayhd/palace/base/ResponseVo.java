package com.yimayhd.palace.base;

import java.io.Serializable;
import java.util.Date;

import com.yimayhd.palace.constant.ResponseStatus;

/**
 * @author wenfeng zhang @ 10/15/14
 */
public class ResponseVo implements Serializable {

	private static final long serialVersionUID = 6372741107786945383L;

	private static final ResponseStatus DEFAULT_STATUS = ResponseStatus.SUCCESS;
	private static final ResponseStatus UNSUCCESSFUL = ResponseStatus.UNSUCCESSFUL;

	public ResponseVo() {
		this.status = DEFAULT_STATUS.VALUE;
		this.message = DEFAULT_STATUS.MESSAGE;
	}

	public ResponseVo(Object data) {
		this.status = DEFAULT_STATUS.VALUE;
		this.data = data;
		this.message = DEFAULT_STATUS.MESSAGE;
		if(null != data && data instanceof Boolean){
			if((boolean)data){
				this.status = DEFAULT_STATUS.VALUE;
			}else{
				this.status = UNSUCCESSFUL.VALUE;
			}
		}
	}

	public ResponseVo(ResponseStatus responseStatus) {
		this.status = responseStatus.VALUE;
		this.message = responseStatus.MESSAGE;
	}

	public ResponseVo(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer status;
	public String message;
	private Long timstamp = new Date().getTime();
	private Object data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getTimstamp() {
		return timstamp;
	}

	public void setTimstamp(Long timstamp) {
		this.timstamp = timstamp;
	}

	public static ResponseVo error(Exception e) {
		ResponseVo responseVo = new ResponseVo(ResponseStatus.ERROR);
		if (e instanceof BaseException) {
			responseVo.setMessage(e.getMessage());
		}
		return responseVo;
	}

	public static ResponseVo error() {
		ResponseVo responseVo = new ResponseVo(ResponseStatus.INVALID_DATA);
		return responseVo;
	}
	
	public static ResponseVo success() {
		return new ResponseVo();
	}

	public static ResponseVo success(Object data) {
		return new ResponseVo(data);
	}
}
