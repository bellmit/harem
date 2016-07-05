package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.Date;

import com.yimayhd.palace.base.BaseModel;

public class SockpuppetDO extends BaseModel implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6605040749208694601L;

	/**
     *  用户ID,所属表字段为sockpuppet.user_id
     */
    private Long userId;

    /**
     *  昵称,所属表字段为sockpuppet.nickname
     */
    private String nickname;

    /**
     *  手机号,所属表字段为sockpuppet.mobile
     */
    private Long mobile;

    /**
     *  用户头像,所属表字段为sockpuppet.avatar
     */
    private String avatar;

    /**
     *  状态（10：删除；20：正常）,所属表字段为sockpuppet.status
     */
    private Integer status;

    /**
     *  创建时间,所属表字段为sockpuppet.gmt_created
     */
    private Date gmtCreated;

    /**
     *  更新时间,所属表字段为sockpuppet.gmt_modified
     */
    private Date gmtModified;


    /**
     * 设置 ID 字段:sockpuppet.id
     *
     * @param id sockpuppet.id, ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 用户ID 字段:sockpuppet.user_id
     *
     * @return sockpuppet.user_id, 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置 用户ID 字段:sockpuppet.user_id
     *
     * @param userId sockpuppet.user_id, 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 昵称 字段:sockpuppet.nickname
     *
     * @return sockpuppet.nickname, 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置 昵称 字段:sockpuppet.nickname
     *
     * @param nickname sockpuppet.nickname, 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取 手机号 字段:sockpuppet.mobile
     *
     * @return sockpuppet.mobile, 手机号
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     * 设置 手机号 字段:sockpuppet.mobile
     *
     * @param mobile sockpuppet.mobile, 手机号
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取 用户头像 字段:sockpuppet.avatar
     *
     * @return sockpuppet.avatar, 用户头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置 用户头像 字段:sockpuppet.avatar
     *
     * @param avatar sockpuppet.avatar, 用户头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取 状态（10：删除；20：正常） 字段:sockpuppet.status
     *
     * @return sockpuppet.status, 状态（10：删除；20：正常）
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置 状态（10：删除；20：正常） 字段:sockpuppet.status
     *
     * @param status sockpuppet.status, 状态（10：删除；20：正常）
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取 创建时间 字段:sockpuppet.gmt_created
     *
     * @return sockpuppet.gmt_created, 创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 设置 创建时间 字段:sockpuppet.gmt_created
     *
     * @param gmtCreated sockpuppet.gmt_created, 创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 获取 更新时间 字段:sockpuppet.gmt_modified
     *
     * @return sockpuppet.gmt_modified, 更新时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置 更新时间 字段:sockpuppet.gmt_modified
     *
     * @param gmtModified sockpuppet.gmt_modified, 更新时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}