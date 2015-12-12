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
@ContextConfiguration(locations = {"classpath:spring-mybatis-test.xml"})
public class TestMapper {

	@Autowired
	HaRoleMapper haRoleMapper;
	
	@Test
	public void test() {
		
		RoleListQuery roleListQuery = new RoleListQuery();
		roleListQuery.setPageNumber(1);
		roleListQuery.setPageSize(5);
		//roleListQuery.setRoleName("name");
		//roleListQuery.setCreateBeginTime("2015-01-01 10:10:23");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(JSON.toJSONString(haRoleMapper.getListNew(roleListQuery)));
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
	@Test
	public void roleDetail() {
		
		System.out.println(JSON.toJSONString(haRoleMapper.roleDetailById(1L)));
		
	}
	
}
