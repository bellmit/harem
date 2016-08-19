package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.HaRoleDO;
import com.yimayhd.palace.model.HaRoleDetail;
import com.yimayhd.palace.model.query.RoleListQuery;

public interface SystemManageService {

	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) throws Exception;
	
	public PageVO<HaRoleDetail> roleDetailById(RoleListQuery roleListQuery) throws Exception;
	
	public boolean updateRoleStatus(HaRoleDO haRoleDO);
	
	public boolean addOrUpdateRoleDetaiStatus(long roleMenuId, int roleStatus, long roleId);
	
}
