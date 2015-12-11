package com.yimayhd.harem.mapper;

import java.util.List;

import com.yimayhd.harem.base.BaseMapper;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.query.RoleListQuery;

/**
 * 角色表（菜单）
 * @author czf
 */
public interface HaRoleMapper extends BaseMapper<HaRoleDO>{

	public List<HaRoleDO> getListNew(RoleListQuery roleListQuery);

}
