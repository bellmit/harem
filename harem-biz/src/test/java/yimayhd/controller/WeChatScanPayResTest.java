package com.yimayhd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author czf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/mvc-servlet.xml", "file:src/main/resources/spring-context.xml"})
public class WeChatScanPayResTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        mapper = new ObjectMapper();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testOrderPayNotice() throws Exception {
        String notice = "<xml>    <appid><![CDATA[wx2421b1c4370ec43b]]></appid>    <attach><![CDATA[支付测试]]></attach>    <bank_type><![CDATA[CFT]]></bank_type>    <fee_type><![CDATA[CNY]]></fee_type>    <is_subscribe><![CDATA[Y]]></is_subscribe>    <mch_id><![CDATA[10000100]]></mch_id>    <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>    <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>    <out_trade_no><![CDATA[1409811653]]></out_trade_no>    <result_code><![CDATA[SUCCESS]]></result_code>    <return_code><![CDATA[SUCCESS]]></return_code>    <sign><![CDATA[0B468682CDB11099701FE305CBE15536]]></sign>    <sub_mch_id><![CDATA[10000100]]></sub_mch_id>    <time_end><![CDATA[20140903131540]]></time_end>    <total_fee>1</total_fee>    <trade_type><![CDATA[JSAPI]]></trade_type>    <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id> </xml>";
        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/orderPay/orderPayNotice", MediaType.APPLICATION_XML_VALUE)
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_ATOM_XML).content(notice.getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }
    @Test
    public void unifiedOrder()throws Exception{
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orderPay/unifiedOrder/f1e25161270e46269d8e72111f2cc644", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！");
        detail.put("return_code","test");
        detail.put("return_msg","test");
        detail.put("appid","test");
        detail.put("mch_id","test");
        detail.put("nonce_str","test");
        detail.put("sign","test");
        detail.put("result_code","test");
        detail.put("err_code","test");
        detail.put("err_code_des","test");

        detail.put("device_info","test");

        detail.put("trade_type","test");
        detail.put("prepay_id","test");
        detail.put("code_url","test");
        detail.put("code_url_time","201506011030");
        detail.put("order_id","test");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/wechatScanPayRess", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }




    // 查询
    @Test
    public void testGetByOrderId() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/wechatScanPayRess/order/tests", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


}
