package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.service.SysUserService;
import com.yimayhd.harem.mapper.SysUserMapper;
import com.yimayhd.harem.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

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
