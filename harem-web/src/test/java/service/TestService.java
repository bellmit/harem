package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.service.SystemManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml"})
public class TestService {

	@Autowired
	SystemManageService systemManageService;
	
	@Test
	public void test() {
		
		RoleListQuery roleListQuery = new RoleListQuery();
		roleListQuery.setPageNumber(0);
		roleListQuery.setPageSize(5);
		roleListQuery.setRoleName("name");
		System.out.println(JSON.toJSONString(systemManageService.getListNew(roleListQuery)));
	
	}
	
}
