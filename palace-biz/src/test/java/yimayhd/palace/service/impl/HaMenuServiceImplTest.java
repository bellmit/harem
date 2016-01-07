package yimayhd.palace.service.impl;

import com.yimayhd.palace.service.HaMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2015/11/2.
 */
public class HaMenuServiceImplTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = {"classpath:*.xml"})
    public class ServePublishServiceImplTest {

        @Autowired
        private HaMenuService haMenuService;

        @Test
        public void getMenuListByUserId(){

        }

    }
}
