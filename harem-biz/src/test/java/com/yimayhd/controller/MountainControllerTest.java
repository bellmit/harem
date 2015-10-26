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
public class MountainControllerTest {

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

    // 增加山头
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是名字！");
        detail.put("intro", "这是简介！");
        detail.put("introduction", "这是介绍！");
        detail.put("categoryId", "111111");
        detail.put("originId", "111111");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/mountains", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除山头
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/mountains/8d458ed3812047c8841d644ca3901a74", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改山头
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "5这是名字！");
        detail.put("intro", "5这是简介！");
        detail.put("introduction", "5这是介绍！");
        detail.put("categoryId", "5111111");
        detail.put("originId", "5111111");
        detail.put("status", "1");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/mountains/1", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询山头的信息  
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains/7436b8994b444416a78348d1b8c38a1a", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询山头时令列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加山头时令
    @Test
    public void testSaveSeason() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("mountainId", "111");
        detail.put("seasonId", "111");
        detail.put("position", 1);
        detail.put("price", 11.11);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/mountains/season", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除山头时令
    @Test
    public void testDeleteSeason() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/mountains/season/e207d0d201624de8b1a3a58d30a26936", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改山头时令
    @Test
    public void testModifySeason() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("mountainId", "6c3cde44814644efaa009cc24eb40232");
        detail.put("seasonId", "be1b216945ca4d779f53b9cd408a0a72");
        detail.put("position", 2);
        detail.put("onsell",1);
        detail.put("presell",0);
        detail.put("price", 511.11);
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/mountains/season/6c3cde44814644efaa009cc24eb40232", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询山头时令的信息
    @Test
    public void testGetSeasonById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains/season/e207d0d201624de8b1a3a58d30a26936", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询山头时令列表
    @Test
    public void testSeasonIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains/season", "json").param("mountainId", "6c3cde44814644efaa009cc24eb40232")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加山头时令1
    @Test
    public void testSaveSeason1() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("mountainId", "6c3cde44814644efaa009cc24eb40232");
        detail.put("seasonId", "111");
        detail.put("position", 1);
        detail.put("price", 11.11);
        detail.put("onsell",1);
        detail.put("presell",1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/mountains/season", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据产地和品类查询对应的山头
    @Test
    public void testGetByOriginCategory() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains/category/9b87350d568b49d09bf94634f548ae6e/origin/bc64b4a445e94d17902a4c0908e6c4c6", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据山头和位置选择对应的时令和该种选择的详情
    @Test
    public void testGetByMountainPosition() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/mountains/6c3cde44814644efaa009cc24eb40232/position/1", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }




}
