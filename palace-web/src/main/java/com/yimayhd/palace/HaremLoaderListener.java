package com.yimayhd.palace;

import com.chinanetcenter.api.util.Config;
import com.yimayhd.palace.config.ResourceConfig;
import com.yimayhd.palace.constant.AttachmentConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created by czf on 2015/11/2.
 */
public class HaremLoaderListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(HaremLoaderListener.class);
    public final static String CONFIG_BEAN_NAME = "palaceProperties";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //注入配置文件
        WebApplicationContext webApplication = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        Properties configProperties = (Properties) webApplication
                .getBean(CONFIG_BEAN_NAME);
        ResourceConfig.getInstance().init(configProperties);

        //
        try {
            //初始化上传config
            ResourceConfig resourceConfig = ResourceConfig.getInstance();
            String ak = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_AK);
            String sk = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_SK);
            String putUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_PUTURL);
            String mgrUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_MGRURL);
            String bucketName = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME);
            String getUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_AUDIO_HOST);
            Config.init(ak, sk, putUrl, getUrl, mgrUrl);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("init chinanetcenter_config error");
            logger.error("init chinanetcenter_config Exception:" + e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
