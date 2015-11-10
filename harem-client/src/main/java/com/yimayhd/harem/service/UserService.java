package com.yimayhd.harem.service;


import com.yimayhd.harem.model.User;

import java.util.List;

/**
 * @author
 */
public interface UserService{

    /**
     * 根据俱乐部ID获取俱乐部成员列表
     * @param clubId 俱乐部ID
     * @return
     * @throws Exception
     */
    List<User> getClubMemberListByClubId(long clubId)throws Exception;

    /**
     * 获取用户列表
     * @param user
     * @return
     * @throws Exception
     */
    List<User> getUserList(User user)throws Exception;
}
