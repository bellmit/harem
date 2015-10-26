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
public class SysUserControllerTest {

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

    // 增加管理员
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("username", "liuhaiming1");
        detail.put("password", "123456");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/sysusers", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除管理员
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/sysusers/0182bae80b4342c9ac1c2c3c83f4dd74", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改管理员密码
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("username", "haiyue");
        detail.put("password", "222222");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/sysusers/0182bae80b4342c9ac1c2c3c83f4dd74", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据主键查询管理员信息
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/sysusers/0182bae80b4342c9ac1c2c3c83f4dd74", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询管理员列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/sysusers", "json").param("username", "11")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 登录
    @Test
    public void testLogin() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("username", "liuhaiming1");
        detail.put("password", "1234561");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/sysusers/login", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
