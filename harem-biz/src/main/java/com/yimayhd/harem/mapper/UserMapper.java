package com.yimayhd.harem.mapper;

import com.yimayhd.harem.base.BaseMapper;
import com.yimayhd.harem.model.User;

public interface UserMapper extends BaseMapper<User> {
    User login(User user)throws Exception;
    long getCountByTel(String tel)throws Exception;
    User getUserDetail(String id)throws Exception;
    void passwordModify(User user)throws Exception;
}