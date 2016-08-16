package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.mapper.HaRoleMenuMapper;
import com.yimayhd.palace.model.HaRoleMenuDO;
import com.yimayhd.palace.service.HaRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色菜单表
 * @author czf
 */
//@Service
public class HaRoleMenuServiceImpl extends BaseServiceImpl<HaRoleMenuDO> implements HaRoleMenuService{

    @Autowired
    private HaRoleMenuMapper haRoleMenuMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haRoleMenuMapper);
    }
}
