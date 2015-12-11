package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.service.ClubService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubServiceImpl implements ClubService {
    @Override
    public List<Club> getList(Club clubVO) throws Exception {
        List<Club> clubList = new ArrayList<Club>();
        int j = 10;
        //是否有查询条件
        for (int i = 0;i <= j;i++){
            Club clubData = new Club();
            clubData.setId((long) i);
            clubData.setName("俱乐部" + i);//交易编号
            clubData.setLogoUrl("/123");
            clubData.setJoinStatus(1);
            clubData.setShowStatus(i / 2 + 1);
            clubData.setJoinNum(Long.valueOf(50 + i));
            clubData.setLimitNum(Long.valueOf(100 + i));
            clubData.setManageUserName("王武" + i);
            clubData.setManageUserLogoUrl("/456");
            clubData.setHasActivityNum(Long.valueOf(30*i));
            clubList.add(clubData);
        }
        return clubList;
    }

    @Override
    public Club getById(long id) throws Exception {
        Club clubData = new Club();
        int i = 3;
        clubData.setId(id);
        clubData.setName("俱乐部" + i);//交易编号
        clubData.setLogoUrl("/123");
        clubData.setJoinStatus(1);
        clubData.setShowStatus(i / 2 + 1);
        clubData.setJoinNum(Long.valueOf(50 + i));
        clubData.setLimitNum(Long.valueOf(100 + i));
        clubData.setManageUserName("王武" + i);
        clubData.setManageUserLogoUrl("/456");
        clubData.setHasActivityNum(Long.valueOf(30*i));

        return clubData;
    }

    @Override
    public Club add(Club club) throws Exception {
        return null;
    }

    @Override
    public void modify(Club club) throws Exception {

    }
}
