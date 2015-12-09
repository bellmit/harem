package dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yimayhd.ic.client.service.item.ResourcePublishService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml"})
public class JUnitDubboTest {

	@Autowired
	ResourcePublishService resourcePublishService;
	
	@Test
	public void test() {
		
		System.out.println();
		System.out.println(resourcePublishService == null);
		System.out.println();
		
	}
	
}
