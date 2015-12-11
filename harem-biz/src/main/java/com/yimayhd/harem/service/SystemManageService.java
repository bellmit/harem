package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.query.RoleListQuery;

public interface SystemManageService {

	public List<HaRoleDO> getListNew(RoleListQuery roleListQuery);
	
}
