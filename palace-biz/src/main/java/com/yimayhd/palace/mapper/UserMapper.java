package com.yimayhd.palace.mapper;

import com.yimayhd.palace.base.BaseMapper;
import com.yimayhd.palace.model.User;

public interface UserMapper extends BaseMapper<User> {
    User login(User user)throws Exception;
    long getCountByTel(String tel)throws Exception;
    User getUserDetail(String id)throws Exception;
    void passwordModify(User user)throws Exception;
}