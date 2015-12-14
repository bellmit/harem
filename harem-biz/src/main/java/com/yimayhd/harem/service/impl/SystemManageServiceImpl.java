package com.yimayhd.harem.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.mapper.HaRoleMenuMapper;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.HaRoleDetail;
import com.yimayhd.harem.model.HaRoleMenuDO;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.service.SystemManageService;

public class SystemManageServiceImpl implements SystemManageService {

	@Autowired
	private HaRoleMapper haRoleMapper;
	
	@Autowired
	private HaRoleMenuMapper haRoleMenuMapper;
	
	@Override
	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) {
		
		List<HaRoleDO> roleList = haRoleMapper.getListNew(roleListQuery);
		long totalCount = haRoleMapper.totalCount(roleListQuery);		
		PageVO<HaRoleDO> pageVo = new PageVO<HaRoleDO>(roleListQuery.getPageNumber(), roleListQuery.getPageSize(), (int)totalCount, roleList);
		
		return pageVo;
	}

	@Override
	public PageVO<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery) throws Exception {
		
		List<HaRoleDetail> roleDetailList = haRoleMapper.roleDetailById(roleListQuery);
		long totalCount = haRoleMapper.roleDetailCount();
		
		PageVO<HaRoleDetail> pageVo = new PageVO<HaRoleDetail>(roleListQuery.getPageNumber(), roleListQuery.getPageSize(), (int)totalCount, roleDetailList);
		
		return pageVo;
	}

	@Override
	public boolean updateRoleStatus(HaRoleDO haRoleDO) {
		
		return haRoleMapper.updateRoleStatus(haRoleDO);
	}

	@Override
	public boolean addOrUpdateRoleDetaiStatus(long menuId, int roleStatus, long roleId) {
		
		List<HaRoleMenuDO> roleMenuList = haRoleMenuMapper.getHaRoleMenuById(menuId);
		
		Iterator<HaRoleMenuDO> it = roleMenuList.iterator();
		boolean hasRole = false;
		HaRoleMenuDO haRoleMenuDo = null;
		
		while(it.hasNext()) {
			
			HaRoleMenuDO haRMDo = it.next();
			if(haRMDo.getHaRoleId() == roleId) {
				hasRole = true;
				haRoleMenuDo = haRMDo;
				haRoleMenuDo.setStatus(roleStatus);
				break;
			}
		}
		
		if (hasRole) {
			
			try {
				haRoleMenuMapper.modify(haRoleMenuDo);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		} else {
			
			HaRoleMenuDO haRoleMenuDO = new HaRoleMenuDO();
			haRoleMenuDO.setHaMenuId(menuId);
			haRoleMenuDO.setHaRoleId(roleId);
			haRoleMenuDO.setStatus(roleStatus);
			boolean result = haRoleMenuMapper.addRoleMenu(haRoleMenuDO);
			
			return result;
		}
		
	}

}
