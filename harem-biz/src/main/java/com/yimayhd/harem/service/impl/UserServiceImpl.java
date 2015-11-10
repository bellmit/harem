package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.UserMapper;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author czf
 */
public class UserServiceImpl implements UserService {


    @Override
    public List<User> getClubMemberListByClubId(long clubId) throws Exception {
        List<User> userList = new ArrayList<User>();
        for(int i = 0;i < 2;i++){
            User user = new User();
            user.setTel("1803926207" + i);
            user.setName("tiny" + i);
            user.setUserName("李四" + i);
            user.setGmtCreated(new Date());
            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<User> getUserList(User user) throws Exception {
        List<User> userList = new ArrayList<User>();
        for(int i = 0;i < 5;i++){
            User userData = new User();
            userData.setId((long) i);
            userData.setUserName("Gost");
            userData.setRealName("宋江");
            userData.setGender(1);
            userData.setCard("4101831900");
            userData.setAge(18);
            userData.setHomePlace("湖南老家");
            userData.setProvinceId((long) 10010);
            userData.setProvinceName("湖南");
            userData.setCityName("郴州");
            userData.setGmtCreated(new Date());
            userData.setRemark("备注");
            userList.add(userData);
        }
        return userList;
    }

}
