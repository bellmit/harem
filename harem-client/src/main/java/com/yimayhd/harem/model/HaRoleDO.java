package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;

/**
 * 角色表（菜单）
 * @table ha_role
 * @author czf
 **/
public class HaRoleDO extends BaseModel {

    private static final long serialVersionUID = 1L;


    private long id; // ID

    private String name; // 角色名称

    private Date gmtCreated; // 创建时间

    private Date gmtModified; // 更新时间

    private int status; // 状态（0：删除；1：正常）


    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
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