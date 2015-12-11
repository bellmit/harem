package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;

/**
 * 角色表（菜单）
 * @table ha_role
 * @author czf
 **/
public class HaRoleDO extends BaseModel {

    private static final long serialVersionUID = 4253103877866042198L;
    private String name; // 角色名称


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}