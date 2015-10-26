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
public class MakerControllerTest {

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

    // 增加制茶师
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "33这是制茶师！");
        detail.put("intro", "这是简介！");
        detail.put("introduction", "这是介绍232！");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/makers", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除制茶师
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/makers/1", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改制茶师
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！5");
        detail.put("intro", "这是简介！5");
        detail.put("introduction", "这是介绍！5");
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/makers/1", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询制茶师的信息  
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers/1", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询制茶师列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加制茶师及工艺
    @Test
    public void testSaveCraft() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("makerId", "0bc106dc11ce440c9c595483760612c0");
        detail.put("craftId", "0421421ee78548908c4c0bd69e42328e");
        detail.put("price", 11.11);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/makers/craft", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除制茶师的工艺
    @Test
    public void testDeleteCraft() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/makers/craft/1bb7876625544e62a5993cadf4fe6cdf", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改制茶师的工艺
    @Test
    public void testModifyCraft() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("price", 11.13);
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/makers/craft/1bb7876625544e62a5993cadf4fe6cdf", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询制茶师的工艺
    @Test
    public void testGetCraft() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers/craft/286f532629e247699f2f0a4a75f8b746", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询制茶师的工艺
    @Test
    public void testGetCraftList() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers/craft", "json").param("makerId", "0bc106dc11ce440c9c595483760612c0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加制茶师的山头
    @Test
    public void testSaveMountain() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("makerId", "0bc106dc11ce440c9c595483760612c0");
        detail.put("mountainId", "afedc7a0c1334cf18fa51f5d3d9b4418");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/makers/mountain", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除制茶师的山头
    @Test
    public void testDeleteMountain() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/makers/mountain/09bac1bde41b416daabea7b92871cb55", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询制茶师的山头
    @Test
    public void testGetMountainList() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers/mountain", "json").param("makerId", "0bc106dc11ce440c9c595483760612c0")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }



    @Test
    public void testSaveMakerCraftMountain() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("makerId", "37f1195eb3d549fdb6f76f8720df25e0");
        detail.put("craftId", "8bc849dd5d6d4fb5a4c6d12f34891ac5");
        detail.put("mountainId", "fcf80cc794414d48a54177dd8c0cd488");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/makers/craft_mountain", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     ## 根据制茶工艺和山头选择制茶师
     ***
     URL:/makers/craft/{craftId}/mountain/{mountainId}
     Param:categoryId,品类ID;mountainId,山头Id
     Method:GET
     ***
     Example:
     request：/makers/craft/8bc849dd5d6d4fb5a4c6d12f34891ac5/mountain/fcf80cc794414d48a54177dd8c0cd488
     Result：
         {
             "status": 200,
             "message": "执行成功！",
             "timstamp": 1431171051965,
             "data": [
                 {
                     "id": "37f1195eb3d549fdb6f76f8720df25e0",
                     "name": "李四"
                 },
                 {
                     "id": "f0dbd3230a204a62ac8d8f0cbe499db9",
                     "name": "张三"
                 }
             ],
             "notice": null
         }


     */
    @Test
    public void testGetListByCraftMountain() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/makers/craft/8bc849dd5d6d4fb5a4c6d12f34891ac5/mountain/6c3cde44814644efaa009cc24eb40232", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
