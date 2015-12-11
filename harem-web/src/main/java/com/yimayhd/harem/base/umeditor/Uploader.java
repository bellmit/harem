package com.yimayhd.harem.base.umeditor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.web.multipart.MultipartFile;

import com.taobao.common.tfs.TfsManager;

/**
 * UEditor文件上传辅助类
 *
 */
public class Uploader {
	// 输出文件地址
	private String url = "";
	// 上传文件名
	private String fileName = "";
	// 状态
	private String state = "";
	// 文件类型
	private String type = "";
	// 原始文件名
	private String originalName = "";
	// 文件大小
	private long size = 0;

	private String title = "";

	// 保存路径
	private String savePath = "upload";
	// 文件允许格式
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png",
			".jpg", ".jpeg", ".bmp" };
	// 文件大小限制，单位KB
	private int maxSize = 10000;

	private HashMap<String, String> errorInfo = new HashMap<String, String>();
	private TfsManager tfsManager;

	private MultipartFile multipartFile;

	public Uploader(MultipartFile multipartFile, TfsManager tfsManager) {
		this.multipartFile = multipartFile;
		this.tfsManager = tfsManager;
		HashMap<String, String> tmp = this.errorInfo;
		tmp.put("SUCCESS", "SUCCESS"); // 默认成功
		tmp.put("NOFILE", "未包含文件上传域");
		tmp.put("TYPE", "不允许的文件格式");
		tmp.put("SIZE", "文件大小超出限制");
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		tmp.put("REQUEST", "上传请求异常");
		tmp.put("IO", "IO异常");
		tmp.put("DIR", "目录创建失败");
		tmp.put("UNKNOWN", "未知错误");

	}

	public void upload() throws Exception {
		if (this.multipartFile == null) {
			this.state = this.errorInfo.get("NOFILE");
			return;
		}
		if (this.multipartFile.getSize() > this.maxSize) {
			this.state = this.errorInfo.get("SIZE");
			return;
		}
		try {
			this.originalName = multipartFile.getOriginalFilename();
			if (!this.checkFileType(this.originalName)) {
				this.state = this.errorInfo.get("TYPE");
				return;
			}
			String suffix = "";
			if (this.originalName.lastIndexOf(".") != -1) {
				suffix = this.originalName.substring(this.originalName.lastIndexOf("."));
			}
			this.fileName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix) + suffix;
			this.type = this.getFileExt(this.originalName);
			this.url = savePath +  this.fileName;
			this.state = this.errorInfo.get("SUCCESS");
			this.size = multipartFile.getSize();
		} catch (Exception e) {
			this.state = this.errorInfo.get("UNKNOWN");
		}
	}

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	private String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setAllowFiles(String[] allowFiles) {
		this.allowFiles = allowFiles;
	}

	public void setMaxSize(int size) {
		this.maxSize = size;
	}

	public long getSize() {
		return this.size;
	}

	public String getUrl() {
		return this.url;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getState() {
		return this.state;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getOriginalName() {
		return this.originalName;
	}
}
