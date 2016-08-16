package com.yimayhd.palace.mapper;

import java.util.List;

import com.yimayhd.palace.base.BaseMapper;
import com.yimayhd.palace.model.HaRoleDO;
import com.yimayhd.palace.model.HaRoleDetail;
import com.yimayhd.palace.model.query.RoleListQuery;

/**
 * 角色表（菜单）
 * @author czf
 */
public interface HaRoleMapper extends BaseMapper<HaRoleDO>{

	public List<HaRoleDO> getListNew(RoleListQuery roleListQuery);

	public List<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery);
	
	public Long totalCount(RoleListQuery roleListQuery);
		
	public Long roleDetailCount();
	
	public boolean updateRoleStatus(HaRoleDO haRoleDO);
}
