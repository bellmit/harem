package com.yimayhd.controller;

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
public class AddressControllerTest {

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


    // 删除用户
    @Test
    public void testDelete() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/addresses/default/8aec5035966f4547ab83b51cd5166e3d", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 查询出所有得省份信息
    @Test
    public void testGetAllRegionList() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses/regions", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据省份查询对应的城市信息
    @Test
    public void testGetCityByRegion() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses/province/61D30251FE3A43A291EB162905AAD169/cities", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


    // 根据市查询对应的区
    @Test
    public void testGetRegionByCity() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses/city/E4004159FFBF4282B8089A87990A775A/regions", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 增加地址
    @Test
    public void testSave() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("userId", "2");
        detail.put("addressee", "收件人2");
        detail.put("province", "1");
        detail.put("city", "1");
        detail.put("address", "1");
        detail.put("tel", "1");
        detail.put("isDefault", 1);
        detail.put("postCode", "1");

        System.out.println(detail.toString());

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/addresses", "json")
            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
            .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 根据主键查询地址的详情
    @Test
    public void testGetById() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/addresses/857e7e44ffdb42929a0b3adb241ffcd0", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    // 修改地址
    @Test
    public void testModify() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", "fd12cff4899d441fac7937444bb2283c");
        objectNode.put("userId", "6");
        objectNode.put("addressee", "收件人6");
        objectNode.put("province", "6");
        objectNode.put("city", "6");
        objectNode.put("address", "6");
        objectNode.put("tel", "6");
        objectNode.put("isDefault", 0);
        objectNode.put("postCode", "6");
        ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("id", "feca3eb3d4ee4e8f8ff1ab92c86cafc2");
        objectNode1.put("userId", "6");
        objectNode1.put("addressee", "收件人6");
        objectNode1.put("province", "6");
        objectNode1.put("city", "6");
        objectNode1.put("address", "6");
        objectNode1.put("tel", "6");
        objectNode1.put("isDefault", 0);
        objectNode1.put("postCode", "6");
        arrayNode.add(objectNode);
        arrayNode.add(objectNode1);
        detail.put("addressList", arrayNode);


        System.out.println(detail.toString());

        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/addresses", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


//
//
//
//    // 修改用户
//    @Test
//    public void testModify() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        detail.put("name", "这是名字！1111111");
//        detail.put("gender", 2);
//
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.put("/users/090851fa989e41fea3299f1256d9d801", "json")
//            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//            .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }
//

//
//    // 分页条件查询用户列表
//    @Test
//    public void testIndex() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/users", "json").param("name", "11")
//            .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//            .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }
//    @Test
//    public void testGetUserBytel() throws Exception {
//        ObjectNode detail = mapper.createObjectNode();
//        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/users", "json").param("name", "11")
//                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
//                .andReturn().getResponse().getContentAsString();
//        System.out.println(result);
//    }

}
