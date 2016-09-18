package com.yimayhd.palace.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xushubing on 2016/9/18.
 */
public class ExpressUtil {
    public final static Map map = new HashMap() {{
        put("shunfeng", "顺丰速运");
        put("sutongwuliu", "圆通");
        put("yunda", "韵达快递");
        put("shentong", "申通快递");
        put("zhongtong", "中通快递");
        put("huitongkuaidi", "百世汇通");
        put("ems", "EMS");
    }};

    public static String getExpressNameByCode(String code) {
        return (String) map.get(code);
    }
}
