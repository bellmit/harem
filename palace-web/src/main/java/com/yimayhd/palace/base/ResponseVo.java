package com.yimayhd.palace.base;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			if(!(boolean)data){
				this.status = UNSUCCESSFUL.VALUE;
				this.message = UNSUCCESSFUL.MESSAGE;
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
			String s=e.getMessage();
			String result=null;
			Pattern p=Pattern.compile("(?m)^\"(.+)\"$");
			Matcher m=p.matcher(s);
			if(m.find()){
				result=m.group(1);
			}
			responseVo.setMessage(result);
		}
		return responseVo;
	}

	public static ResponseVo error() {
		ResponseVo responseVo = new ResponseVo(ResponseStatus.INVALID_DATA);
		return responseVo;
	}

	public static ResponseVo error(ResponseStatus res) {
		ResponseVo responseVo = new ResponseVo(res);
		return responseVo;
	}
	
	public static ResponseVo success() {
		return new ResponseVo();
	}

	public static ResponseVo success(Object data) {
		return new ResponseVo(data);
	}
}
