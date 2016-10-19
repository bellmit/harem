package com.yimayhd.palace.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import com.yimayhd.palace.util.HandleFilesUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.service.TfsService;
import org.springframework.beans.factory.annotation.Value;

/**
 * TFS服务扩展
 * 
 * @author yebin
 *
 */
public class TfsServiceImpl implements TfsService {
	private static final String prefix = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
			+ "    <meta charset=\"UTF-8\">\n"
			+ "    <meta http-equiv=\"x-ua-compatible\" content=\"IE=edge,chrome=1\">\n"
			+ "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no\">\n"
			+ "    <title></title>\n" + "    <style>img{width: 100%;}</style>" + "</head>\n" + "<body>";
	private static final String suffix = "</body>\n" + "</html>";
	@Autowired
	private TfsManager tfsManager;

	@Value("${palace.tfsRootPath}")
	private String tfsRootPath ;

	@Override
	public String publishHtml5(String body) throws BaseException {
		if (StringUtils.isBlank(body)) {
			return "";
		}
		// String encodeHtml = "<meta http-equiv=\"Content-Type\"
		// content=\"text/html; charset=utf-8\" \n/>";
		String html5 = prefix + body + suffix;
		byte[] bytes = null;
		try {
			bytes = html5.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			bytes = html5.getBytes();
		}
		// byte[] bytes = body.getBytes();
		String tfsCode = "";
		try {
			tfsCode = tfsManager.saveFile(bytes, null, ".html");
		} catch (Exception e) {
			throw new BaseException(e, "Html5上传失败：html={0}", body);
		}
		return tfsCode + ".html";
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
				content = content.replace(prefix, "").replace(suffix, "");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
		return content;
	}

	//发送给部分人，先从tfs中获取csv文件，在把csv转成txt传tfs,在得到地址
	public String tfsFileConvert(String fileName,String fileFormat){
		String txtFileName="";
		String filePath = tfsRootPath + fileName;
		Set<String> setString = HandleFilesUtils.getDistinctStringFromTFS(filePath);

		if(null != setString && setString.size()>0){
			txtFileName = tfsFileUpload(setString,fileFormat);
		}
		return txtFileName;
	}

	//把文件转成txt后上传到tfs返回一个地址
	public String tfsFileUpload(Set<String> str,String fileFormat){
		StringBuilder sb = new StringBuilder();
		for (String s:str) {
			sb.append(s).append("\n");
		}
		byte[] bytes = sb.toString().getBytes();
		String tfsCode = tfsManager.saveFile(bytes, null,fileFormat);
		return tfsCode;
	}

	public String tfsFileUpload(Set<String> str,String fileFormat,boolean sizeCheck,long limitSize) throws Exception{
		if(sizeCheck){
			if(null != str && limitSize >str.size()){
				throw new Exception("数据长度超过限制，当前个数："+str.size()+",限制个数："+limitSize);
			}
		}
		return tfsFileUpload(str,fileFormat);
	}
	public String tfsFileConvert(String fileName,String fileFormat,boolean sizeCheck,long limitSize)throws Exception{
		String txtFileName="";
		String filePath = tfsRootPath + fileName;
		Set<String> setString = HandleFilesUtils.getDistinctStringFromTFS(filePath);

		if(null != setString && setString.size()>0){
			if(sizeCheck){
				 txtFileName = tfsFileUpload(setString,fileFormat,sizeCheck,limitSize);
			}else{
				txtFileName = tfsFileUpload(setString,fileFormat);
			}
		}
		return txtFileName;
	}

}
