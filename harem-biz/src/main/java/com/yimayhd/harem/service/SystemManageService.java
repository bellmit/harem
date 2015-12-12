package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.query.RoleListQuery;

public interface SystemManageService {

	public PageVO<HaRoleDO> getListNew(RoleListQuery roleListQuery) throws Exception;
	
}
