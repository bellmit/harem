package com.yimayhd.harem.base;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class JsonResultUtil {
    public static void jsonResult(HttpServletResponse response,Object entity) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String jsonString = JSON.toJSONString(new ResponseVo(entity));
        out.println(jsonString);
        out.flush();
        out.close();
    }
}
