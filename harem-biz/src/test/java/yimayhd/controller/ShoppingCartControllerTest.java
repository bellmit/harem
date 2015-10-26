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
public class ShoppingCartControllerTest {

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

    // 添加购物车
    @Test
    public void testSave() throws Exception {
        ObjectNode goods = mapper.createObjectNode();
//        goods.put("id", "d0fe10ce70e247c7a698c9ee28ca44a1");
        goods.put("goodsType", 6);
        goods.put("goodsStatus", 1);
        goods.put("goodsWeight", 66.66);
        goods.put("mountainSeasonId", "6452a06d1ea440abb829df67b667f917");
        goods.put("makerCraftId", "f2b90c5d00be48b7974ba143964a7f2a");
        goods.put("processId", "2");
        goods.put("packId", "4f464758b29e448b8b50e169282a2dfa");

        ObjectNode shoppingCart = mapper.createObjectNode();
        shoppingCart.put("userId", "86d51fbc52c94ad38d19b060ec8945be");
        shoppingCart.put("goods", goods);

        System.out.println(shoppingCart.toString());

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/carts", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(shoppingCart.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询用户购物车里得商品信息
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/carts/user/86d51fbc52c94ad38d19b060ec8945be", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 删除购物车里得商品
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/carts/user/86d51fbc52c94ad38d19b060ec8945be/goods/f252dd76695346ecaa84bfe6cf43857c", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询用户的商品详情
    @Test
    public void testGetGoods() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/carts/user/86d51fbc52c94ad38d19b060ec8945be/goods/f252dd76695346ecaa84bfe6cf43857c", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

//
//    // 修改用户
//    @Test
//    public void testModify() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        detail.put("name", "这是名字！5");
//        detail.put("intro", "这是简介！5");
//        detail.put("introduction", "这是介绍！5");
//
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/origins/1", "json")
//            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//            .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }
//
//    // 查询用户的信息
//    @Test
//    public void testGetById() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/origins/1", "json")
//            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//            .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }
//
//    // 分页条件查询用户列表
//    @Test
//    public void testIndex() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/origins", "json")
//            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//            .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }

}
