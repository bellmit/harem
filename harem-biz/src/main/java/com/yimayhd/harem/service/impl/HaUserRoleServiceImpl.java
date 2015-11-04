package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaUserRoleMapper;
import com.yimayhd.harem.model.HaUserRoleDO;
import com.yimayhd.harem.service.HaUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户角色表
 * @author czf
 */
//@Service
public class HaUserRoleServiceImpl extends BaseServiceImpl<HaUserRoleDO> implements HaUserRoleService{

    @Autowired
    private HaUserRoleMapper haUserRoleMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haUserRoleMapper);
    }
}
