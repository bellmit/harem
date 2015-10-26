package com.yimayhd.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
 * @author liuhaiming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/mvc-servlet.xml", "file:src/main/resources/spring-context.xml"})
public class OrderControllerTest {

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

    // 添加订单
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("userId", "86d51fbc52c94ad38d19b060ec8945be");
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode goods1 = mapper.createObjectNode();
        goods1.put("id","d0fe10ce70e247c7a698c9ee28ca44a1");
        ObjectNode goods2 = mapper.createObjectNode();
        goods2.put("id","ee30071ec94c4e00a90edc2428d84176");
        arrayNode.add(goods1);
        arrayNode.add(goods2);
        detail.put("goodsList", arrayNode);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询订单的详情
    @Test
    public void testGetDetailById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders", "json").param("userId", "40280039f38d4a53b459ddf5d0ce8448").param("orderStatus","0").param("isPaging", "0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }



    @Test
    public void testGetAllOrder() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/all", "json").param("isPaging", "0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
        Object obj = JSONUtils.parse(result);
        System.out.println(obj.toString());
    }

    @Test
    public void testGetOrderAddressUser() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/no_deliver", "json").param("isPaging", "0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testOrderDeliver() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/orders/deliver/85623f3920c34caba6da85b61b81b617", "json").param("remark", "yuantong 13213")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testGetOrderDelivered() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/delivered", "json").param("isPaging", "0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 添加订单
    @Test
    public void testSave1() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
//        detail.put("id", UUIDUtil.generateUniqueKey());
        detail.put("userId", "86d51fbc52c94ad38d19b060ec8945be");
//        detail.put("createdOn", new Date().toString());
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode goods1 = mapper.createObjectNode();
        goods1.put("id","d0fe10ce70e247c7a698c9ee28ca44a1");
        ObjectNode goods2 = mapper.createObjectNode();
        goods2.put("id","ee30071ec94c4e00a90edc2428d84176");
        arrayNode.add(goods1);
        arrayNode.add(goods2);
        detail.put("goodsList", arrayNode);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
