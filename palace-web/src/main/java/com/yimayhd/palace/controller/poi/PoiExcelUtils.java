package com.yimayhd.palace.controller.poi;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @ClassName: PoiExcelUtils
 * @author create by yushengwei @ 2015年10月20日 下午5:17:07
 * @Description: poi excel下载
 */
public class PoiExcelUtils {
    public static String encodeFilename(String filename,HttpServletRequest request) {
        // 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
        // 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
        String agent = request.getHeader("USER-AGENT");
        System.out.println("--------------------agent---"+agent);
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE")) || (agent != null) && (-1 != agent.indexOf("Trident"))) {
                return URLEncoder.encode(filename, "UTF-8");
            }else if ((agent != null) && (-1 != agent.indexOf("Mozilla"))){
                return  new String(filename.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }
}
