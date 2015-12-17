package com.yimayhd.harem.service;

public interface TfsService {
	/**
	 * 将富文本格式化为Html5页发布到tfs
	 * 
	 * @param body
	 *            富文本
	 * @return tfsCode
	 */
	String publishHtml5(String body);

	/**
	 * 读取Html5
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	String readHtml5(String code) throws Exception;
}
