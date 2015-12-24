package com.yimayhd.harem.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.service.TfsService;

/**
 * TFS服务扩展
 * 
 * @author yebin
 *
 */
public class TfsServiceImpl implements TfsService {
	@Autowired
	private TfsManager tfsManager;

	@Override
	public String publishHtml5(String body) throws BaseException {
		if (StringUtils.isBlank(body)) {
			return "";
		}
		// String encodeHtml = "<meta http-equiv=\"Content-Type\"
		// content=\"text/html; charset=utf-8\" \n/>";
		String encodeHtmlHead = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta http-equiv=\"x-ua-compatible\" content=\"IE=edge,chrome=1\">\n"
				+ "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no\">\n"
				+ "    <title></title>\n" + "    <style>img{width: 100%;}</style>" + "</head>\n" + "<body>";
		String encodeHtmlFoot = "</body>\n" + "</html>";
		/*String html5 = encodeHtmlHead + body + encodeHtmlFoot;
		byte[] bytes = null;
		try {
			bytes = html5.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			bytes = html5.getBytes();
		}*/
		byte[] bytes = body.getBytes();
		String tfsCode = "";
		try {
			tfsCode = tfsManager.saveFile(bytes, null, "html");
		} catch (Exception e) {
			throw new BaseException(e, "Html5上传失败：html={0}", body);
		}
		return tfsCode;
	}

	@Override
	public String readHtml5(String tfsFileName) throws Exception {
		String content = "";
		if (StringUtils.isNotBlank(tfsFileName)) {
			ByteArrayOutputStream os = null;
			try {
				os = new ByteArrayOutputStream();
				boolean result = tfsManager.fetchFile(tfsFileName, null, os);
				if (result) {
					byte[] bytes = os.toByteArray();
					content = new String(bytes, "utf-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		return content;
	}

}
