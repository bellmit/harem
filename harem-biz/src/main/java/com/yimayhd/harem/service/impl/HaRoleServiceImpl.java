package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaRoleMapper;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.service.HaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
