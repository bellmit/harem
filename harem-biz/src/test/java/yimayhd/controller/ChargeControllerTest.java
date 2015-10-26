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
 * @author liuhaiming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/mvc-servlet.xml", "file:src/main/resources/spring-context.xml"})
public class ChargeControllerTest {

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

    // 增加用户
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！");
        detail.put("intro", "这是简介！");
        detail.put("introduction", "这是介绍！");
        detail.put("basicPrice", 22.22);
        detail.put("perPrice", 22.33);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/charges", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除用户
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/charges/0d56eeac098548d3a431f2445a73b71a", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改用户
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！5");
        detail.put("intro", "这是简介！5");
        detail.put("introduction", "这是介绍！5");
        detail.put("basicPrice", 522.22);
        detail.put("perPrice", 522.33);
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/charges/0d56eeac098548d3a431f2445a73b71a", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询用户的信息  
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/charges/0d56eeac098548d3a431f2445a73b71a", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询用户列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/charges", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
