package com.yimayhd.palace.model.guide;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxp on 2016/8/22.
 */
public class ScenicVO implements Serializable{

    /**
     * 资源id
     */
    private long id;

    /**
     * 景区名称
     */
    private String name;

    /**
     * 详细地址
     */
    private String locationText;

    /**
     * 所在省id
     */
    private long locationProvinceId;

    /**
     * 所在省名称
     */
    private String locationProvinceName;

    /**
     * 所在市id
     */
    private long locationCityId;

    /**
     * 所在市名称
     */
    private String locationCityName;

    /**
     * 所在镇id
     */
    private long locationTownId;

    /**
     * 所在镇名称
     */
    private String locationTownName;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 等级
     */
    private int level;
    private String levelDesc;

    /**
     * 主题id
     */
    private long subjectId;

    /**
     * 主题名称
     */
    private String subjectName;

    /**
     * 纬度
     */
    private double locationX;

    /**
     * 经度
     */
    private double locationY;

    private String openTime;

    private String address;

    public String getLevelDesc() {
        return levelDesc;
    }

    public ScenicVO setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
        return this;
    }

    public String getAddress() {
        return locationProvinceName+" "+locationCityName+" "+locationTownName;
    }

    public ScenicVO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public ScenicVO setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

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

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public long getLocationProvinceId() {
        return locationProvinceId;
    }

    public void setLocationProvinceId(long locationProvinceId) {
        this.locationProvinceId = locationProvinceId;
    }

    public String getLocationProvinceName() {
        return locationProvinceName;
    }

    public void setLocationProvinceName(String locationProvinceName) {
        this.locationProvinceName = locationProvinceName;
    }

    public long getLocationCityId() {
        return locationCityId;
    }

    public void setLocationCityId(long locationCityId) {
        this.locationCityId = locationCityId;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public long getLocationTownId() {
        return locationTownId;
    }

    public void setLocationTownId(long locationTownId) {
        this.locationTownId = locationTownId;
    }

    public String getLocationTownName() {
        return locationTownName;
    }

    public void setLocationTownName(String locationTownName) {
        this.locationTownName = locationTownName;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }
}
