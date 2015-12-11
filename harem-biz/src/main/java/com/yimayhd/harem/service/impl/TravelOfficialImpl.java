package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.service.TravelOfficialService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialImpl implements TravelOfficialService{
    @Override
    public List<TravelOfficial> getList(TravelOfficial travelOfficial) throws Exception {
        List<TravelOfficial> travelOfficialList = new ArrayList<TravelOfficial>();
        for (int i = 0; i < 10; i++) {
            TravelOfficial travelOfficialData = new TravelOfficial();
            travelOfficialData.setId((long) i);
            travelOfficialData.setTitle("穿梭在云端的日子【蜜月之旅】");
            travelOfficialData.setRegionId((long) 100);
            travelOfficialData.setRegionName("西藏");
            User user = new User();
            user.setName("孙六");
            user.setId((long) 2);
            travelOfficialData.setUser(user);
            travelOfficialData.setTravelStatus(1);
            travelOfficialData.setPublishDate(new Date());
            travelOfficialData.setPraiseNum(88);
            travelOfficialData.setCollectionNum(880);
        }
        return null;
    }

    @Override
    public TravelOfficial getById(long id) throws Exception {
        TravelOfficial travelOfficialData = new TravelOfficial();
        travelOfficialData.setId(id);
        travelOfficialData.setTitle("穿梭在云端的日子【蜜月之旅】");
        travelOfficialData.setRegionId((long) 100);
        travelOfficialData.setRegionName("西藏");
        User user = new User();
        user.setName("孙六");
        user.setId((long) 2);
        travelOfficialData.setUser(user);
        travelOfficialData.setTravelStatus(1);
        travelOfficialData.setPublishDate(new Date());
        travelOfficialData.setPraiseNum(88);
        travelOfficialData.setCollectionNum(880);
        return travelOfficialData;
    }

    @Override
    public TravelOfficial add(TravelOfficial travelOfficial) throws Exception {
        return null;
    }

    @Override
    public void modify(TravelOfficial travelOfficial) throws Exception {

    }
}
