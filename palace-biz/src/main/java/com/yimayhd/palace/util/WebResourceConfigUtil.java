package com.yimayhd.palace.util;

import com.yimayhd.palace.config.ResourceConfig;

/**
 * 暴露给view用的
 * Created by Administrator on 2015/11/3.
 */
public class WebResourceConfigUtil {
    private final static String TFS_ROOT_PATH_KEY = "palace.tfsRootPath";
    private final static String STATIC_RESOURCE_PAHT_KEY = "palace.staticResourcesPath";
    private final static String ACTION_DEFAULT_FONT_PATH_KEY= "palace.actionDefaultFontPath";
    private final static String ACTION_UPLOAD_FILE_PATH_KEY = "actionUploadFilePath";
    private final static String ACTION_UPLOAD_FILES_PATH_KEY = "actionUploadFilesPath";

    public static String getTfsRootPath() {
        return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
    }

    public static String getStaticResourcesPath(){
        return ResourceConfig.getInstance().getValueByKey(STATIC_RESOURCE_PAHT_KEY);
    }

    public static String getActionDefaultFontPath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_DEFAULT_FONT_PATH_KEY);
    }

    public static String getActionUploadFilePath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILE_PATH_KEY);
    }

    public static String getActionUploadFilesPath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_UPLOAD_FILES_PATH_KEY);
    }
    public static String getResourceVersion(){
        return "20151227";
    }
}
