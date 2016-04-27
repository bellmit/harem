package com.yimayhd.palace.util;

import com.yimayhd.palace.config.ResourceConfig;
import com.yimayhd.palace.constant.Constant;

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

    //分销
    private final static String ITEM_IMG_URI_PATH = "item.img.uri";
    private final static String RESOURCE_PATH_JIUXIU = "resource.path.jiuxiu";
    private final static String ENV = "env";
    private final static String ROOT_PATH = "root.path";
    private final static String FILEGW_URL = "filegw.url";
    
    public static String getTfsRootPath() {
        return ResourceConfig.getInstance().getValueByKey(TFS_ROOT_PATH_KEY);
    }
    public static String getRootPath() {
    	return ResourceConfig.getInstance().getValueByKey(ROOT_PATH);
    }
    public static String getResourcePathJiuXiu() {
    	return ResourceConfig.getInstance().getValueByKey(RESOURCE_PATH_JIUXIU);
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
        return "8";
    }
    public static String getItemImgUrlPath(){
    	return ResourceConfig.getInstance().getValueByKey(ITEM_IMG_URI_PATH);
    }
    public static String getFilegwUrl(){
    	return ResourceConfig.getInstance().getValueByKey(FILEGW_URL);
    }
    public static boolean isTestMode(){
    	String e = ResourceConfig.getInstance().getValueByKey(ENV) ;
    	if( e != null && Constant.ENV_PROD.equalsIgnoreCase(e) ){
    		return false;
    	}
    	return true;
    }
}
