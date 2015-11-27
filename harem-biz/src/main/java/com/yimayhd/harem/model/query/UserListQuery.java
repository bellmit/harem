package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/9.
 */
public class UserListQuery extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = -603714674325753343L;
	private String userName;
    private String tel;
    private Long cityName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getCityName() {
        return cityName;
    }

    public void setCityName(Long cityName) {
        this.cityName = cityName;
    }
}
