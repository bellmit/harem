package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;

/**
 * 用户角色表
 * @table ha_user_role
 * @author czf
 **/
public class HaUserRoleDO extends BaseModel {


    private static final long serialVersionUID = 4484764335558537915L;
    private Long haUserId; // 用户ID

    private Long haRoleId; // 角色ID

    public Long getHaUserId() {
        return haUserId;
    }

    public void setHaUserId(Long haUserId) {
        this.haUserId = haUserId;
    }

    public Long getHaRoleId() {
        return haRoleId;
    }

    public void setHaRoleId(Long haRoleId) {
        this.haRoleId = haRoleId;
    }
}