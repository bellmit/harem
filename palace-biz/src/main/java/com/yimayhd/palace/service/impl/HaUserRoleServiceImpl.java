package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.mapper.HaUserRoleMapper;
import com.yimayhd.palace.model.HaUserRoleDO;
import com.yimayhd.palace.service.HaUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

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
