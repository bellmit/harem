package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.mapper.HaRoleMapper;
import com.yimayhd.palace.model.HaRoleDO;
import com.yimayhd.palace.service.HaRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色表（菜单）
 * @author czf
 */
//@Service
public class HaRoleServiceImpl extends BaseServiceImpl<HaRoleDO> implements HaRoleService{

    @Autowired
    private HaRoleMapper haRoleMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haRoleMapper);
    }
}
