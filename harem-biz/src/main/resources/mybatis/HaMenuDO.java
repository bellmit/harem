package mybatis;

import com.yimayhd.harem.base.BaseModel;

/**
 * 菜单表
 * @table ha_menu
 * @author czf
 **/
public class HaMenuDO extends BaseModel {

    private static final long serialVersionUID = 1L;


    private long id; // ID主键

    private String name; // 菜单名称

    private String linkUrl; // 连接地址

    private int level; // 菜单等级

    private long parentId; // 父级菜单ID

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

    public void setLinkUrl(String linkUrl){
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setParentId(long parentId){
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
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