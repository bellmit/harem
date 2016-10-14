package com.yimayhd.palace.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/10/12
 * @Description
 */
public class HandleFilesUtils {
    private static final Logger logger = LoggerFactory.getLogger(HandleFilesUtils.class);
    //把读的文件内容去重
    public static Set<String> getDistinctString(String path){
        InputStream input = getInputStreamForURL(path);
        Set<String> str = null;
        try {
            List<String> listLines = IOUtils.readLines(input);
            if(!CollectionUtils.isEmpty(listLines)){
                str = new HashSet(listLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    //从tfs上读文件
    public static InputStream getInputStreamForURL(String path) {
        URL url = null;
        InputStream is =null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }


    public static void main(String[] arsf){
        String filePath="http://img.test.yimayholiday.com/v1/tfs/T1JqJTByxT1R4P1CrK.csv";
        HandleFilesUtils s = new HandleFilesUtils();
        Set<String> string = s.getDistinctString(filePath);
        for (String str :string  ) {
            System.out.println(str);
        }
        HandleFilesUtils sf = new HandleFilesUtils();
        //String name = sf.fileUploadToTFS(string);

    }
}
