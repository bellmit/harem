package com.yimayhd.model;

import com.yimayhd.base.BaseModel;

import java.util.List;

/**
 * @author czf
 *         用户的基本信息
 */
public class User extends BaseModel {

    private static final long serialVersionUID = 339789620192906779L;
    /**
     * 名字
     */
    private String name;

    /**
     * 性别（0：无，1：男，2：女）
     */
    private Integer gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 用户名
     */

    private String userName;
    /**
     * 密码
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 注册电话
     */
    private String tel;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //注册验证码
    private String code;

    //默认地址ID
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}