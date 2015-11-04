package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

/**
 * Created by Administrator on 2015/11/2.
 */
public class Club extends BaseModel {
    private String name;//名称
    private String logoUrl;//logo
    private String picturePoster;//
    private int joinStatus;//是否允许加入状态
    private int showStatus;//是否显示状态
    private long joinNum;//成员数
    private long limitNum;//成员限额
    private String manageUserName;//部长名称
    private String manageUserLogoUrl;//部长logo
    private long hasActivityNum;//累计活动

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(int joinStatus) {
        this.joinStatus = joinStatus;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public long getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(long joinNum) {
        this.joinNum = joinNum;
    }

    public long getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(long limitNum) {
        this.limitNum = limitNum;
    }

    public String getManageUserName() {
        return manageUserName;
    }

    public void setManageUserName(String manageUserName) {
        this.manageUserName = manageUserName;
    }

    public String getManageUserLogoUrl() {
        return manageUserLogoUrl;
    }

    public void setManageUserLogoUrl(String manageUserLogoUrl) {
        this.manageUserLogoUrl = manageUserLogoUrl;
    }

    public long getHasActivityNum() {
        return hasActivityNum;
    }

    public void setHasActivityNum(long hasActivityNum) {
        this.hasActivityNum = hasActivityNum;
    }
}
