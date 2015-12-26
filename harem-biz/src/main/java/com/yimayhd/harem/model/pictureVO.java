package com.yimayhd.harem.model;

/**
 * Created by czf on 2015/12/26.
 * 资源图片集用
 */
public class PictureVO {
    private long id;//图片id
    private int index;//图片索引
    private String name;//图片原名称
    private String value;//图片url
    private boolean isTop;//是否置顶
    private boolean modify;//是否修改
    private boolean isdel;//是否删除


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean isdel() {
        return isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }
}
