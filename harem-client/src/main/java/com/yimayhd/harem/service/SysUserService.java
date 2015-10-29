package com.yimayhd.harem.service;


import com.yimayhd.harem.base.BaseService;
import com.yimayhd.harem.model.SysUser;

/**
 * @author liuhaiming
 */
public interface SysUserService extends BaseService<SysUser> {

    int countByUsername(String username) throws Exception;

    SysUser login(SysUser sysUser) throws Exception;
}
