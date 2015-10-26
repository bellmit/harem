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
public class CraftControllerTest {

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

    // 增加制茶工艺
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！");
        detail.put("intro", "这是简介！");
        detail.put("introduction", "这是介绍！");
        detail.put("categoryId", "111111");
        detail.put("ratio", 22.22);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/crafts", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除制茶工艺
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/crafts/76b02e9e7b2d4d18a93fbfdbd566c0bc", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改制茶工艺
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "5这是名字！");
        detail.put("intro", "5这是简介！");
        detail.put("introduction", "5这是介绍！");
        detail.put("categoryId", "5111111");
        detail.put("ratio", 522.22);
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/crafts/76b02e9e7b2d4d18a93fbfdbd566c0bc", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询制茶工艺的信息  
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/crafts/0421421ee78548908c4c0bd69e42328e", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询制茶工艺列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/crafts", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
         ## 根据品类查询对应的加工工序
         ***
         URL:/crafts/category/{categoryId}
         Param:categoryId,品类ID
         Method:GET
         ***
         Example:
         request：/crafts/category/9564d86b4d504c2eb01c6432989d8028
         Result：
             {
                 "status": 200,
                 "message": "执行成功！",
                 "timstamp": 1431171340692,
                 "data": [
                     {
                         "id": "8bc849dd5d6d4fb5a4c6d12f34891ac5",
                         "name": "最好得",
                         "ratio": 0.2
                     },
                     {
                         "id": "33c8ea3ab0954740acca5fef7b6f067b",
                         "name": "好的工艺",
                         "ratio": 0.1
                     }
                 ],
                 "notice": null
             }

      */
    @Test
    public void testGetListByCategory() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/crafts/category/9564d86b4d504c2eb01c6432989d8028", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据山头查询对应的制茶师详情
    @Test
    public void testGetByMountain() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/crafts/mountain/fcf80cc794414d48a54177dd8c0cd488", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
