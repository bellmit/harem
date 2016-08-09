package com.yimayhd.palace.mapper;

import com.yimayhd.palace.base.BaseMapper;
import com.yimayhd.palace.model.HaRoleMenuDO;

/**
 * 角色菜单表
 * @author czf
 */
public interface HaRoleMenuMapper extends BaseMapper<HaRoleMenuDO> {

	public boolean addRoleMenu(HaRoleMenuDO haRoleMenuDO);
	
	public HaRoleMenuDO getHaRoleMenuById(long id);
	
}
