package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.merchant.MerchantService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.user.client.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author czf
 */
public class UserServiceImpl implements UserService {

    //@Autowired
    //private MerchantService merchantServiceRef;


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

    @Override
    public List<UserDO> getMemberByUserId(TradeMemberQuery tradeMemberQuery) throws Exception {
        MerchantPageQueryVO merchantPageQueryVO = new MerchantPageQueryVO();
        merchantPageQueryVO.setMerchantUserId(tradeMemberQuery.getMerchantUserId());
        merchantPageQueryVO.setNickName(tradeMemberQuery.getNickName());
        merchantPageQueryVO.setCity(tradeMemberQuery.getCityName());
        merchantPageQueryVO.setMobile(tradeMemberQuery.getTel());
        //MemResult<List<UserDO>> result = merchantServiceRef.findPageUsersByMerchant(merchantPageQueryVO);
        //List<UserDO> userDOList= result.getValue();
        List<UserDO> userDOList = new ArrayList<UserDO>();
        for (int i = 0; i < 5; i++) {
            UserDO userDOData = new UserDO();
            userDOData.setId(i);
            userDOData.setNick("kaka");
            userDOData.setGender(i / 2);
            userDOData.setMobile("110");
            userDOData.setCertId("410123556487468535");
            userDOData.setBirthday(new Date());
            userDOData.setProvince("伊拉克");
            userDOData.setCity("中东");
            userDOData.setGmtCreate(new Date());
            userDOList.add(userDOData);
        }
        return userDOList;
    }

}
