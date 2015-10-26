package com.yimayhd.util;

/**
 * 参数工具类
 * 主要用来对参数的判断等操作
 *
 * Created by wqy on 15-5-3.
 */
public class ArgsUtil {

    public static <T> T notNull(final T arg, final String name) {
        if(arg == null)
            throw new IllegalArgumentException(name + "can't be null");
        return arg;
    }


}
