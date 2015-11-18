package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.UserListQuery;

/**
 * Created by Administrator on 2015/11/9.
 */
public class UserVO {
    private User user;
    private UserListQuery userListQuery;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserListQuery getUserListQuery() {
        return userListQuery;
    }

    public void setUserListQuery(UserListQuery userListQuery) {
        this.userListQuery = userListQuery;
    }
}
