package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

/**
 * 管理员信息
 *
 * @author czf
 */
public class SysUser extends BaseModel {

    private static final long serialVersionUID = -3494522455036529588L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

}