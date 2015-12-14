package mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.model.HaRoleDO;
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
		
		RoleListQuery roleListQuery = new RoleListQuery();
		roleListQuery.setRoleId(1L);
		roleListQuery.setPageNumber(1);
		roleListQuery.setPageSize(5);
		System.out.println(JSON.toJSONString(haRoleMapper.roleDetailById(roleListQuery)));
		
	}
	
	@Test
	public void roleDetailById() {
		
		System.out.println(JSON.toJSONString(haRoleMapper.roleDetailCount()));
		
	}
	
	@Test
	public void testTotalCount() {
		
		RoleListQuery roleListQuery = new RoleListQuery();
		roleListQuery.setPageNumber(1);
		roleListQuery.setPageSize(5);
		//roleListQuery.setRoleName("name");
		//roleListQuery.setCreateBeginTime("2015-01-01 10:10:23");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(JSON.toJSONString(haRoleMapper.totalCount(roleListQuery)));
		System.out.println();
		System.out.println();
		System.out.println();
		
	}

	@Test
	public void updateRoleStatus() {
		
		HaRoleDO haRoleDO = new HaRoleDO();
		haRoleDO.setId(1);
		haRoleDO.setStatus(1);
		haRoleMapper.updateRoleStatus(haRoleDO);
		
	}
}
