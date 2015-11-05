package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * 菜单表
 * @table ha_menu
 * @author czf
 **/
public class HaMenuDO extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String name; // 菜单名称

    private String linkUrl; // 连接地址

    private Integer level; // 菜单等级

    private Long parentId; // 父级菜单ID

    private List<HaMenuDO> haMenuDOList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<HaMenuDO> getHaMenuDOList() {
        return haMenuDOList;
    }

    public void setHaMenuDOList(List<HaMenuDO> haMenuDOList) {
        this.haMenuDOList = haMenuDOList;
    }
}