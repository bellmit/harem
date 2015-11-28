package com.yimayhd.harem.service;


import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.user.client.domain.UserDO;

import java.util.List;

/**
 * @author czf
 */
public interface UserService{

    //B2C部分
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

    //商贸部分

    /**
     * 根据商贸用户id获取会员列表
     * @return 会员列表
     * @throws Exception
     */
    List<UserDO> getMemberByUserId(TradeMemberQuery tradeMemberQuery)throws Exception;
}
