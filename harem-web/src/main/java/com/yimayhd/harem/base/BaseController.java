package com.yimayhd.harem.base;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.exception.NoticeException;

/**
 * @author wenfeng zhang
 * 
 * @update yebin 2015/11/17
 */
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected TfsManager tfsManager;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	protected ResponseVo handleException(Exception e) {
		logger.error(e.getMessage(), e);
		if (e instanceof HttpMessageNotReadableException || e instanceof NumberFormatException
				|| e instanceof InvalidPropertyException) {
			return new ResponseVo(ResponseStatus.DATA_PARSE_ERROR.VALUE,
					ResponseStatus.DATA_PARSE_ERROR.MESSAGE + e.getLocalizedMessage());
		} else if (e instanceof NoticeException) {
			return new ResponseVo(ResponseStatus.FORBIDDEN.VALUE, e.getMessage());
		} else if (e instanceof BaseException) {
			return new ResponseVo(ResponseStatus.FORBIDDEN.VALUE, e.getMessage());
		}
		return new ResponseVo(ResponseStatus.ERROR.VALUE, ResponseStatus.ERROR.MESSAGE + e.getLocalizedMessage());
	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 等价于request.getParameter(name).
	 */
	protected String get(final String name) {
		return request.getParameter(name);
	}

	protected Integer getInteger(final String name) {
		final String str = request.getParameter(name);
		if (StringUtils.isNotBlank(str)) {
			return Integer.valueOf(str);
		}
		return null;
	}

	protected Long getLong(final String name) {
		final String str = request.getParameter(name);
		if (StringUtils.isNotBlank(str)) {
			return Long.valueOf(str);
		}
		return null;
	}

	/**
	 * 等价于request.setAttribute(key, value).
	 */
	protected void put(final String key, final Object value) {
		request.setAttribute(key, value);
	}

	/**
	 * 保存文件到tfs
	 * 
	 * @param body
	 * @return
	 */
	protected String publishHtml5(String body) {
		// String encodeHtml = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" \n/>";
		String encodeHtmlHead = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta http-equiv=\"x-ua-compatible\" content=\"IE=edge,chrome=1\">\n"
				+ "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no\">\n"
				+ "    <title></title>\n" + "    <style>img{width: 100%;}</style>" + "</head>\n" + "<body>";
		String encodeHtmlFoot = "</body>\n" + "</html>";
		String html5 = encodeHtmlHead + body + encodeHtmlFoot;
		byte[] bytes = null;
		try {
			bytes = html5.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			bytes = html5.getBytes();
		}
		String tfsName = tfsManager.saveFile(bytes, null, "html");
		return tfsName;
	}
}
