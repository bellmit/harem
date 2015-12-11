package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class MenuDO extends BaseModel {

    private static final long serialVersionUID = -3841255297152329985L;
    private String name;

    private String linkUrl;

    private Integer level;

    private Long parentId;


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
}