package com.yimayhd.palace.controller.vo;

import java.io.Serializable;

/**
 * Created by moon_princess on 16/5/30.
 */
public class MerchantCategoryVO implements Serializable {
    private static final long serialVersionUID = -6353080914080230627L;

    private long id;
    private String name;
    private String pic;
    private int type;
    private long parentId;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
