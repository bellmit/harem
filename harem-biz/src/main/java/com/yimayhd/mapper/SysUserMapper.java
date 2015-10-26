package com.yimayhd.mapper;

import com.yimayhd.base.BaseMapper;
import com.yimayhd.model.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

    int countByUsername(String username) throws Exception;

    SysUser login(SysUser sysUser) throws Exception;
}