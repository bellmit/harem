package com.yimayhd.harem.util;

import com.yimayhd.harem.config.ResourceConfig;

/**
 * 暴露给view用的
 * Created by Administrator on 2015/11/3.
 */
public class WebResourceConfigUtil {
    private final static String TFS_ROOT_PATH_KEY = "harem.tfsRootPath";
    private final static String STATIC_RESOURCE_PAHT_KEY = "harem.staticResourcesPath";
    private final static String ACTION_DEFAULT_FONT_PATH_KEY= "harem.actionDefaultFontPath";

    public static String getTfsRootPath() {
        return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
    }

    public static String getStaticResourcesPath(){
        return ResourceConfig.getInstance().getValueByKey(STATIC_RESOURCE_PAHT_KEY);
    }

    public static String getActionDefaultFontPath(){
        return ResourceConfig.getInstance().getValueByKey(ACTION_DEFAULT_FONT_PATH_KEY);
    }
}
