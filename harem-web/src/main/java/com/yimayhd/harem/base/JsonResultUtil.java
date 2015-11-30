package com.yimayhd.harem.base;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2015/11/28.
 */
public class JsonResultUtil {
    public static void jsonResult(HttpServletResponse response,Object entity) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        List<Object> ob = (List<Object>) entity;
        PrintWriter out = response.getWriter();
        String jsonStr1 = JSON.toJSONString(ob.get(1));
        String jsonStr = JSON.toJSONString(entity);
        String jsonString = JSON.toJSONString(new ResponseVo(entity));
        out.println(jsonString);
        out.flush();
        out.close();    }
}
