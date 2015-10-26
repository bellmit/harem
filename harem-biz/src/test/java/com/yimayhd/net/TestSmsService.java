package com.yimayhd.net;

//import com.com.yimayhd.service.SmsService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by wqy on 15-5-4.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/resources/mvc-servlet.xml", "file:src/main/resources/spring-context.xml"})
public class TestSmsService {

    /*@Autowired
    private SmsService smsService;

    @Test
    public void testPublishMsg4Register() throws Exception{
        String phoneNumber = "18039262076";
        String templateAlias = "messageCode";
        List<NameValueBlock> blocks = new ArrayList<NameValueBlock>();
        blocks.add(new NameValueBlock("#messageCode#", "123321"));
        Integer result = smsService.publishMsg(phoneNumber, blocks, templateAlias);

        System.out.println("testPublishMsg4Register result : " + result);
    }*/

}
