package com.yimayhd.harem.mapper;

import java.util.List;

import com.yimayhd.harem.base.BaseMapper;
import com.yimayhd.harem.model.HaRoleMenuDO;

/**
 * 角色菜单表
 * @author czf
 */
public interface HaRoleMenuMapper extends BaseMapper<HaRoleMenuDO> {

	public boolean addRoleMenu(HaRoleMenuDO haRoleMenuDO);
	
	public HaRoleMenuDO getHaRoleMenuById(long id);
	
}
