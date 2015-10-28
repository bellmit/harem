package com.yimayhd.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1034671331121855153L;

    /**
     * 主键
     */
    protected long id;

    /**
     * 创建时间
     */
    protected Date createdOn;

    /**
     * 更新时间
     */
    protected Date updatedOn;

    /**
     * 1-正常0-删除
     */
    protected int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
