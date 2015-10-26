package com.yimayhd.mapper;

import com.yimayhd.base.BaseMapper;
import com.yimayhd.model.User;

public interface HaMenuMapper extends BaseMapper<User> {
    User login(User user)throws Exception;
    long getCountByTel(String tel)throws Exception;
    User getUserDetail(String id)throws Exception;
    void passwordModify(User user)throws Exception;
}