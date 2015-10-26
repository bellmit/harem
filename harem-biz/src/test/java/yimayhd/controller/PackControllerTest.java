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
public class PackControllerTest {

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

    // 增加包装
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "这是包装！");
        detail.put("intro", "这是简介！");
        detail.put("introduction", "这是介绍！");
        detail.put("categoryId", "111111");
        detail.put("price", 22.22);
        detail.put("basicWeight", 22.22);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/packs", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


    // 删除包装
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/packs/2ca2256edd2e4e88b1f14bc731bef7cf", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改包装
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "5这是包装！");
        detail.put("intro", "5这是简介！");
        detail.put("introduction", "5这是介绍！");
        detail.put("categoryId", "1511111");
        detail.put("price", 522.22);
        detail.put("basicWeight", 522.22);
        detail.put("status", 1);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/packs/2ca2256edd2e4e88b1f14bc731bef7cf", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询包装的信息  
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/packs/2ca2256edd2e4e88b1f14bc731bef7cf", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 分页条件查询包装列表
    @Test
    public void testIndex() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/packs", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
           ## 根据品类查询对应的包装列表
           ***
           URL:/packs/category/{categoryId}
           Param:categoryId,品类ID
           Method:GET
           ***
           Example:
           Request：/packs/category/9b87350d568b49d09bf94634f548ae6e
           Result：
             {
                 "status": 200,
                 "message": "执行成功！",
                 "timstamp": 1431171657415,
                 "data": [
                     {
                         "id": "d638c781e2e7404080dca48f7ac8c2a2",
                         "name": "大包装",
                         "price": 22,
                         "basicWeight": 1,
                         "basicPrice": 2
                     },
                     {
                         "id": "4f464758b29e448b8b50e169282a2dfa",
                         "name": "小包装",
                         "price": 12,
                         "basicWeight": 1,
                         "basicPrice": 2
                     }
                 ],
                 "notice": null
             }
     * */
    @Test
    public void testGetListByCategoryId() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/packs/category/9b87350d568b49d09bf94634f548ae6e", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
