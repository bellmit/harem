package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;

/**
 * 用户角色表
 * @table ha_user_role
 * @author czf
 **/
public class HaUserRoleDO extends BaseModel {

    private static final long serialVersionUID = 1L;


    private long id; // ID

    private long haUserId; // 用户ID

    private long haRoleId; // 角色ID

    private Date gmtCreated; // 创建时间

    private Date gmtModified; // 更新时间

    private int status; // 状态（0：删除；1：正常）


    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setHaUserId(long haUserId){
        this.haUserId = haUserId;
    }

    public long getHaUserId() {
        return haUserId;
    }

    public void setHaRoleId(long haRoleId){
        this.haRoleId = haRoleId;
    }

    public long getHaRoleId() {
        return haRoleId;
    }

    public void setGmtCreated(Date gmtCreated){
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}