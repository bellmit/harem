package com.yimayhd.palace;

import com.chinanetcenter.api.util.Config;
import com.yimayhd.palace.config.ResourceConfig;
import com.yimayhd.palace.constant.AttachmentConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author wuzhengfei358
 */
public class ContextStartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ContextStartUpListener.class);
    private boolean isStarted = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Task task = new Task();
        task.start();
    }

    private class Task extends Thread {

        public void run() {
            if (isStarted) {
                return;
            }
            isStarted = true;
          /*  try {
                //初始化上传config
                ResourceConfig resourceConfig = ResourceConfig.getInstance();
                String ak = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_AK);
                String sk = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_SK);
                String putUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_PUTURL);
                String mgrUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_MGRURL);
                String bucketName = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME);
                String getUrl = resourceConfig.getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_AUDIO_HOST);;
                Config.init(ak, sk, putUrl, getUrl, mgrUrl);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("init chinanetcenter_config error");
                logger.error("init chinanetcenter_config Exception:" + e);
            }*/
        }

    }


}
