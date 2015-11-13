package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

/**
 * Created by Administrator on 2015/11/13.
 */
public class Region extends BaseModel{
    private String name;
    private Integer level;
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
