package com.yimayhd.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 封装HttpClient工具类，用于第三方接口的请求调用。
 *
 * Created by wqy on 15-4-30.
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送 短信
     * @param URL 短信接口地址
     * @param params 请求参数集合 CorpID. Pwd. Mobile. Content.
     * @return 短信接口返回值
     * */
    public static Integer publishSms(final String URL, List<NameValuePair> params) throws Exception {

        Integer status = 0;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        RequestConfig requestConfig = RequestConfig.custom()        // 请求超时设置
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .build();
        httpGet.setConfig(requestConfig);
        String requestParams = EntityUtils.toString(new UrlEncodedFormEntity(params, "GBK"));   // 注意 参数编码
        System.out.println("URLENCODE : " + new UrlEncodedFormEntity(params, "GBK"));
        httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + requestParams));
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        try {
            String phoneNumber = params.get(2).toString();      // 规定 第三个参数 为手机号

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                status = Integer.parseInt(EntityUtils.toString(httpEntity));
                System.out.println("status : " + status);
                EntityUtils.consume(httpEntity);
                LOGGER.info("SMS Interface INFO : Phone Number(" + phoneNumber + ") had successfully published.");
            }
            LOGGER.error("SMS Interface ERROR : Phone Number( " + phoneNumber + ") had unsuccessfully published!");

        } finally {
            httpResponse.close();
        }
        return status;
    }

    /**
     * 通用GET请求
     * @param URL 请求地址
     * @param encode 请求编码格式
     * @param params 请求参数集合
     * @return 返回码，1表示正常请求
     *
     * */
    public static Integer getMethod(final String URL, final String encode, final List<NameValuePair> params) throws Exception {

        Integer result = 0;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(8000)        // 设置连接超时
                .setSocketTimeout(30000)        // 设置响应超时
                .build();
        httpGet.setConfig(requestConfig);
        String requestUrl = EntityUtils.toString(new UrlEncodedFormEntity(params, encode));
        httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + requestUrl));
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        try {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                LOGGER.info("HttpClient Request Success" + URL + "请求成功！" + EntityUtils.toString(httpEntity));      // 日志待完成
                EntityUtils.consume(httpEntity);

                return 1;
            }
            LOGGER.error("HttpClient Request Error" + URL + "请求失败！");
        } finally {
            httpClient.close();
        }
        return result;
    }

    /**
     * 通用GET请求
     * @param URL 请求地址
     * @param requestParam 请求参数字符串(key1=value1&key2=value2..)
     * @return 返回码，1表示正常请求
     *
     * */
    public static Integer getMethod(final String URL, final String requestParam) throws Exception {

        Integer result = 0;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(8000)        // 设置连接超时
                .setSocketTimeout(30000)        // 设置响应超时
                .build();
        httpGet.setConfig(requestConfig);
        httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + requestParam));
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        try {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();

                LOGGER.info("HttpClient Request Success" + URL + "请求成功！" + EntityUtils.toString(httpEntity));      // 日志待完成
                EntityUtils.consume(httpEntity);

                return 1;
            }
            LOGGER.error("HttpClient Request Error" + URL + "请求失败！");
        } finally {
            httpClient.close();
        }
        return result;
    }


    /**
     * 通用POST请求
     * @param URL 请求地址
     * @param requestParam 请求参数字符串(key1=value1&key2=value2..)
     * @param encode 字符编码
     * @return 返回码，1表示正常请求
     *
     * */
    public static Integer postMethod(final String URL, final String requestParam, final String encode) throws Exception {

        Integer result = 0;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(8000)        // 设置连接超时
                .setSocketTimeout(30000)        // 设置响应超时
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new UrlEncodedFormEntity(URLEncodedUtils.parse(requestParam, Charset.forName(encode))));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            String a = "11";
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();

                LOGGER.info("HttpClient Request Success" + URL + "请求成功！" + EntityUtils.toString(httpEntity));      // 日志待完成
                EntityUtils.consume(httpEntity);
                String b = "22";

                return 1;
            }
            LOGGER.error("HttpClient Request Error" + URL + "请求失败！");
        } finally {
            httpClient.close();
        }
        return result;
    }

    /**
     * 通用POST请求
     * @param URL 请求地址
     * @param param 请求参数集合
     * @return 返回码，1表示正常请求
     *
     * */
    public static Integer postMethod(final String URL, final List<NameValuePair> param) throws Exception {

        Integer result = 0;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(8000)        // 设置连接超时
                .setSocketTimeout(30000)        // 设置响应超时
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new UrlEncodedFormEntity(param));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();

                LOGGER.info("HttpClient Request Success" + URL + "请求成功！" + EntityUtils.toString(httpEntity));      // 日志待完成
//                System.out.println(EntityUtils.toString(httpEntity));
                EntityUtils.consume(httpEntity);

                return 1;
            }
            LOGGER.error("HttpClient Request Error" + URL + "请求失败！");
        } finally {
            httpClient.close();
        }
        return result;
    }

}
