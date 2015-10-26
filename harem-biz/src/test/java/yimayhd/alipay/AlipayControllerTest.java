package com.yimayhd.alipay;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/mvc-servlet.xml", "file:src/main/resources/spring-context.xml"})
public class AlipayControllerTest {

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

    public void testAsyncReturn() throws Exception {

        System.out.println();
    }


    /**
     * 模拟异步返回信息
     *
     * */
    @Test
    public void testAscyn() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("name", "white");
        detail.put("age", 123);

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/alipayDirectTrades/notify_return", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    /**
     * 测试支付流程
     *
     * */
    @Test
    public void testPay() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
//        detail.put("orderNo", "20150517134110733");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/alipayDirectTrades/pay/20150517134110733", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    /**
     * 测试支付流程
     *
     * */
    @Test
    public void testPay1() throws Exception {
        ObjectNode detail = mapper.createObjectNode();
        detail.put("id", "0b59c38ea921428593399bcfbb877bf2");
        detail.put("orderNo", "20150517134110733");

        String result = this.mockMvc.perform(MockMvcRequestBuilders.post("/alipayDirectTrades/pay1", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

    @Test
    public void testGet() throws Exception {
        ObjectNode detail = mapper.createObjectNode();

        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/alipayDirectTrades/notify_return/trade_no=2014040311001004370000361525&out_trade_no=36" +
                "18810634349901", "json")
                .characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).content(detail.toString().getBytes()))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void test11() throws Exception {
        //示例和我们网站模拟相同的提交,我们网站跳转到支付宝却提示验证错误
        // 提交的这个信息都是一致的 不用说其他的参数了 是用的
        /**
         我都打印比较过了
         * */
        String out = "<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=utf-8\" method=\"get\"><input type=\"hidden\" name=\"sign\" value=\"44898e7d628576bc6ed5a3a60d86505f\"/><input type=\"hidden\" name=\"_input_charset\" value=\"utf-8\"/><input type=\"hidden\" name=\"subject\" value=\"产地武夷山大红袍\"/><input type=\"hidden\" name=\"total_fee\" value=\"0.01\"/><input type=\"hidden\" name=\"sign_type\" value=\"MD5\"/><input type=\"hidden\" name=\"service\" value=\"create_direct_pay_by_user\"/><input type=\"hidden\" name=\"notify_url\" value=\"http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp\"/><input type=\"hidden\" name=\"partner\" value=\"2088911646876372\"/><input type=\"hidden\" name=\"seller_email\" value=\"share@yimayhd.com\"/><input type=\"hidden\" name=\"out_trade_no\" value=\"20150611183916353\"/><input type=\"hidden\" name=\"payment_type\" value=\"1\"/><input type=\"hidden\" name=\"return_url\" value=\"http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp\"/><input type=\"submit\" value=\"确认\" style=\"display:none;\"></form><script>document.forms['alipaysubmit'].submit();</script>\n";
        String demo = "<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=utf-8\" method=\"get\"><input type=\"hidden\" name=\"sign\" value=\"44898e7d628576bc6ed5a3a60d86505f\"/><input type=\"hidden\" name=\"_input_charset\" value=\"utf-8\"/><input type=\"hidden\" name=\"subject\" value=\"产地武夷山大红袍\"/><input type=\"hidden\" name=\"total_fee\" value=\"0.01\"/><input type=\"hidden\" name=\"sign_type\" value=\"MD5\"/><input type=\"hidden\" name=\"service\" value=\"create_direct_pay_by_user\"/><input type=\"hidden\" name=\"notify_url\" value=\"http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp\"/><input type=\"hidden\" name=\"partner\" value=\"2088911646876372\"/><input type=\"hidden\" name=\"seller_email\" value=\"share@yimayhd.com\"/><input type=\"hidden\" name=\"out_trade_no\" value=\"20150611183916353\"/><input type=\"hidden\" name=\"payment_type\" value=\"1\"/><input type=\"hidden\" name=\"return_url\" value=\"http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp\"/><input type=\"submit\" value=\"确认\" style=\"display:none;\"></form><script>document.forms['alipaysubmit'].submit();</script>\n";

        System.out.println(out.equals(demo));
    }


}
