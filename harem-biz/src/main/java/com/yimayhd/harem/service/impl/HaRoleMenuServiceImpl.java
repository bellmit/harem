package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaRoleMenuMapper;
import com.yimayhd.harem.model.HaRoleMenuDO;
import com.yimayhd.harem.service.HaRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
