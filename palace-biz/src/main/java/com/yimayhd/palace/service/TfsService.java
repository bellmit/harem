package com.yimayhd.palace.service;

import com.yimayhd.palace.base.BaseException;

import java.util.Set;

public interface TfsService {
	/**
	 * 将富文本格式化为Html5页发布到tfs
	 * 
	 * @param body
	 *            富文本
	 * @return tfsCode
	 * @throws BaseException 
	 */
	String publishHtml5(String body) throws BaseException;

	/**
	 * 读取Html5
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	String readHtml5(String code) throws Exception;

	/**
	 * 把tfs文件fileName,转成另一种文件格式fileFormat
	 *@author create by yushengwei on 2016/10/18
	 * @param fileName
	 * @param fileFormat
	 * @return String
	 */
	public String tfsFileConvert(String fileName,String fileFormat);

	/**
	 * 把文件上传到tfs上
	 *@author create by yushengwei on 2016/10/18
	 * @param str
	 * @param fileFormat
	 * @return String
	 */
	public String tfsFileUpload(Set<String> str,String fileFormat);

	/**
	 * 把文件上传到tfs上
	 *@author create by yushengwei on 2016/10/18
	 * @param str
	 * @param fileFormat
	 * @return String
	 */
	public String tfsFileUpload(Set<String> str,String fileFormat,boolean sizeCheck,long limitSize) throws Exception;
	public String tfsFileConvert(String fileName,String fileFormat,boolean sizeCheck,long limitSize)throws Exception;

}
