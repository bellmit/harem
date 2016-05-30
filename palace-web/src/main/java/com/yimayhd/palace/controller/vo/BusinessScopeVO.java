package com.yimayhd.palace.controller.vo;

import java.io.Serializable;

/**
 * Created by moon_princess on 16/5/30.
 */
public class BusinessScopeVO implements Serializable{
    private static final long serialVersionUID = 3127611760998067857L;

    private long id;
    private String Name;
    private long parentId;
    private long status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
