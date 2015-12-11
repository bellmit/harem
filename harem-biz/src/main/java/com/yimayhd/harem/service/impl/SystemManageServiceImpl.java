package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.service.SystemManageService;

public class SystemManageServiceImpl implements SystemManageService {

	@Autowired
	private HaRoleMapper haRoleMapper;
	
	@Override
	public List<HaRoleDO> getListNew(RoleListQuery roleListQuery) {
		
		return haRoleMapper.getListNew(roleListQuery);
	}

}
