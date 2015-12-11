package com.yimayhd.harem.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.base.BaseTravelController;
import com.yimayhd.harem.base.umeditor.Uploader;

/**
 * 百度编辑器-配置
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/umeditor")
public class UeditorController extends BaseTravelController {
	@Autowired
	private TfsManager tfsManager;

	@RequestMapping(value = "/imageUp")
	public @ResponseBody String imageUp(@RequestParam(value = "upfile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		System.out.println(request.getParameter("upfile"));
		response.setCharacterEncoding("utf-8");
		Uploader up = new Uploader(file, tfsManager);
		up.setSavePath("");
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		up.setAllowFiles(fileType);
		up.setMaxSize(1024 * 1024); // 单位B
		up.upload();

		String callback = request.getParameter("callback");

		String result = "{\"name\":\"" + up.getFileName() + "\", \"originalName\": \"" + up.getOriginalName()
				+ "\", \"size\": " + up.getSize() + ", \"state\": \"" + up.getState() + "\", \"type\": \""
				+ up.getType() + "\", \"url\": \"" + up.getUrl() + "\"}";

		result = result.replaceAll("\\\\", "\\\\");

		if (callback == null) {
			return result;
		} else {
			return "<script>" + callback + "(" + result + ")</script>";
		}
	}

	@RequestMapping(value = "/getContent")
	public void getContent(HttpServletResponse response, PrintWriter out) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String content = request.getParameter("myEditor");
		response.getWriter().print("<div class='content'>" + content + "</div>");
	}

}
