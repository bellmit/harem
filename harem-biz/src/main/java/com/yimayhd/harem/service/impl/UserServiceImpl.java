package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.UserMapper;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author czf
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(userMapper);
    }
    public User login(User user) throws Exception {
        return userMapper.login(user);
    }
    public long getCountByTel(String tel) throws Exception {
        return userMapper.getCountByTel(tel);
    }

    @Override
    public User getUserDetail(String id) throws Exception {
        return userMapper.getUserDetail(id);
    }

    @Override
    public void passwordModify(User user) throws Exception {
        userMapper.passwordModify(user);
    }

}
