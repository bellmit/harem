package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;

/**
 * 角色菜单表
 * @table ha_role_menu
 * @author czf
 **/
public class HaRoleMenuDO extends BaseModel {

    private static final long serialVersionUID = 1L;


    private long id; // 

    private long haMenuId; // 

    private long haRoleId; // 

    private Date gmtCreated; // 创建时间

    private Date gmtModified; // 更新时间

    private int status; // 状态（0：删除；1：正常）


    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setHaMenuId(long haMenuId){
        this.haMenuId = haMenuId;
    }

    public long getHaMenuId() {
        return haMenuId;
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