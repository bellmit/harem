package com.yimayhd.harem.config;

import java.util.Map;
import java.util.Properties;

/**
 * 注入的配置信息
 * Created by Administrator on 2015/11/2.
 */
public class ResourceConfig {
    private Map<Object,Object> map;
    private ResourceConfig(){}
    private static ResourceConfig resourceConfig = new ResourceConfig();
    public static void init(Properties properties){
        resourceConfig.map = properties;
    }
    public static  ResourceConfig getInstance(){
        return resourceConfig;
    }
    public  String getValueByKey(String key){
        return map.get(key).toString();
    }
}
