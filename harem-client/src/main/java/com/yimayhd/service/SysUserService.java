package com.yimayhd.service;


import com.yimayhd.base.BaseService;
import com.yimayhd.model.SysUser;

/**
 * @author liuhaiming
 */
public interface SysUserService extends BaseService<SysUser> {

    int countByUsername(String username) throws Exception;

    SysUser login(SysUser sysUser) throws Exception;
}
