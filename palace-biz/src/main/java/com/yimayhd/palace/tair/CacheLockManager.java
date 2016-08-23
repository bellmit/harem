package com.yimayhd.palace.tair;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xushubing on 2016/7/25.
 */
public class CacheLockManager {

    @Autowired
    private TcCacheManager cacheManager;
    private static final boolean DEFAULT_VALUE = true;
    private static final int DEFAULT_VERSION = 6;
    private static final int DEFAULT_TIME = 180;

    public boolean checkSubmitByCache(String key) {
        return cacheManager.addToTair(key, DEFAULT_VALUE, DEFAULT_VERSION, DEFAULT_TIME);
    }

    public void deleteKey(String key) {
        cacheManager.deleteFromTair(key);
    }
}
