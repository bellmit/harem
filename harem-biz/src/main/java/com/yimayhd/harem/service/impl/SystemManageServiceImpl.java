package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.service.SystemManageService;

public class SystemManageServiceImpl implements SystemManageService {

	@Autowired
	private HaRoleMapper haRoleMapper;
	
	@Override
	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) {
		
		List<HaRoleDO> roleList = haRoleMapper.getListNew(roleListQuery);
		long totalCount = haRoleMapper.totalCount(roleListQuery);
		
		PageVO<HaRoleDO> pageVo = new PageVO<HaRoleDO>(roleListQuery.getPageNumber(), roleListQuery.getPageSize(), (int)totalCount, roleList);
		
		return pageVo;
	}

}
