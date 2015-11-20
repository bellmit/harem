package com.yimayhd.harem.controller;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.util.WebResourceConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
    private TfsManager tfsManager;

    public TfsManager getTfsManager() {
        return tfsManager;
    }
    @Autowired
    public void setTfsManager(TfsManager tfsManager) {
        this.tfsManager = tfsManager;
    }

    /**
     * 上传页面
     * @return 上传页面
     * @throws Exception
     */
    @RequestMapping(value = "/toUpload", method = RequestMethod.GET)
    public
    String toUpload(HttpServletRequest request) throws Exception {
        request.setAttribute("hello", new Date());
        return "/demo/upload";
    }
    /**
     * 上传页面
     * @return 上传页面
     * @throws Exception
     */
    @RequestMapping(value = "/toUpload2", method = RequestMethod.GET)
    public
    String toUpload2(HttpServletRequest request) throws Exception {
        request.setAttribute("hello", new Date());
        return "/demo/upload2";
    }

    /**
     * 上传单个文件
     * @return 文件名称
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseVo uploadFile(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix) + suffix;
        return new ResponseVo(tfsName);
    }

    /**
     * 上传多个文件
     * @param request
     * @return 文件名称数组
     * @throws Exception
     */
    @RequestMapping("/files")
    @ResponseBody
    public ResponseVo uploadFiles(HttpServletRequest request) throws Exception {
        Map<String,String> map = new HashMap();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multipartRequest.getFileNames();
            while(iterator.hasNext()){
                MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
                String fileName=multipartFile.getOriginalFilename();
                String suffix=fileName.substring(fileName.lastIndexOf("."));
                String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix)  + suffix;
                map.put(fileName,tfsName);
            }
        return new ResponseVo(map);

    }

    /**
     * 富文本编辑上传单个文件
     * @param request
     * @return 文件地址
     * @throws Exception
     */
    @RequestMapping("/ckeditorFile")
    @ResponseBody
    public Object ckeditorFile(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
        String tfsName = WebResourceConfigUtil.getTfsRootPath() + tfsManager.saveFile(multipartFile.getBytes(), null, null);
        return tfsName;

    }


    /**
     * 上传文件（富文本编辑）
     * @return 上传文件
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/ckeditor", method = RequestMethod.POST)
    public ResponseVo uploadCKEditor(@RequestParam(value = "activityDetailWeb", required = false)String detail,@RequestParam(value = "activityDetailApp", required = false)String detaill) throws Exception {
        //保存文件到tfs
        String encodeHtml = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
        String detailTfs = tfsManager.saveFile((encodeHtml+detail).getBytes("utf-8"), null, "html");
        //返回文件名
        return new ResponseVo(detailTfs);
    }
}
