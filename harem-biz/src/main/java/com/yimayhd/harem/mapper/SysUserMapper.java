package com.yimayhd.harem.mapper;

import com.yimayhd.harem.base.BaseMapper;
import com.yimayhd.harem.model.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

    int countByUsername(String username) throws Exception;

    SysUser login(SysUser sysUser) throws Exception;
}