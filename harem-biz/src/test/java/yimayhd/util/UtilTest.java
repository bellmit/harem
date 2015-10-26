package com.yimayhd.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wqy on 15-5-14.
 */
public class UtilTest {


    @Test
    public void testCreateLinkStringArr() throws Exception {

        Map<String, String[]> map = new HashMap<String, String[]>();
        String [] strArray = new String[2];
        strArray [0] = "1";
        strArray [1] = "2";
        map.put("b", strArray);
        map.put("a", strArray);
        String str = AlipayUtils.createLinkStringArr(map);
//        System.out.println(str);

        System.out.println(UUIDUtil.generateUniqueKey());
        System.out.println(UUID.randomUUID().toString());
    }
}
