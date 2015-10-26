package com.yimayhd.service.impl;

import com.yimayhd.base.BaseServiceImpl;
import com.yimayhd.mapper.SysUserMapper;
import com.yimayhd.model.SysUser;
import com.yimayhd.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author czf
 */
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(sysUserMapper);
    }

    @Override
    public int countByUsername(String username) throws Exception {
        return sysUserMapper.countByUsername(username);
    }

    @Override
    public SysUser login(SysUser sysUser) throws Exception {
        return sysUserMapper.login(sysUser);
    }
}
