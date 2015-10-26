package com.yimayhd.net;

import com.yimayhd.util.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqy on 15-4-29.
 */
public class TestHttpClientPost {

    private static final String USER = "SY0107";
    private static final String PASSWD = "123456";
    private static String SBT_URL = "http://202.185.215.202:8080/ws/Send2.aspx";


    public static void main(String[] args) throws Exception {
//        testPost1();
//        testGet1();
    }

    public static void test0() throws Exception {

    }
    public static void testPost1 () throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(SBT_URL);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("GorpID", USER));
        nvps.add(new BasicNameValuePair("Pwd", PASSWD));
        nvps.add(new BasicNameValuePair("Mobile", "17605314501"));
        nvps.add(new BasicNameValuePair("Content", "ok!!"));
//        System.out.println(nvps.get(3).toString());
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpClient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            String body = EntityUtils.toString(entity2, "GBK");
            System.out.println("response : " + body);

            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }

    }

    public static void  testGet1() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://202.85.215.202:8080/ws/Send2.aspx?CorpID=SY0107&Pwd=123456&Mobile=13283808232&Content=ok");
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        System.out.println("execute time : " +new Date().toString());
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            String result = EntityUtils.toString(entity1);
            System.out.println("result :" + result);
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }

    @Test
    public void  testPost2() throws Exception {
        String url = "http://127.0.0.1:2014/";
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("Name", "tom"));
        nvps.add(new BasicNameValuePair("Age", "123"));
        Integer result = HttpClientUtils.postMethod(url, nvps);
        System.out.println(result);
    }

    @Test
    public void testNotice() throws Exception {
        String url = "http://127.0.0.1:5555/api/notice";
        Integer result = HttpClientUtils.getMethod(url, "notice=123");
        System.out.println(result);
    }
}