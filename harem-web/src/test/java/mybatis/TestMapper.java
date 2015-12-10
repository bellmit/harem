package mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.model.query.RoleListQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-test.xml"})
public class TestMapper {

	@Autowired
	HaRoleMapper haRoleMapper;
	
	@Test
	public void test() {
		
		RoleListQuery roleListQuery = new RoleListQuery();		
		System.out.println(JSON.toJSONString(haRoleMapper.getListNew(roleListQuery)));
	}
	
}
