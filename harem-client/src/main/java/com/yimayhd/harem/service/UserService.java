package com.yimayhd.harem.service;


import com.yimayhd.harem.base.BaseService;
import com.yimayhd.harem.model.User;

/**
 * @author liuhaiming
 */
public interface UserService extends BaseService<User> {
    User login(User user) throws Exception;

    /**
     * 查询电话注册的数量
     * @param tel 电话号码
     * @return
     * @throws Exception
     */
    long getCountByTel(String tel) throws Exception;

    /**
     * 查询用户基础信息
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    User getUserDetail(String id) throws Exception;

    /**
     * 通过电话修改密码
     * @param user 用户对象
     * @throws Exception
     */
    void passwordModify(User user) throws Exception;

}
