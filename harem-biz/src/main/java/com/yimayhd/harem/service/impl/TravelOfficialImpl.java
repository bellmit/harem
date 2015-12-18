package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TravelOfficialListQuery;
import com.yimayhd.harem.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.snscenter.client.dto.TravelSpecialDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialImpl implements TravelOfficialService{

    @Autowired
    private SnsCenterService snsCenterService;


    @Override
    public PageVO<TravelOfficial> getList(TravelOfficialListQuery travelOfficialListQuery) throws Exception {
//        List<TravelOfficial> travelOfficialList = new ArrayList<TravelOfficial>();
//        for (int i = 0; i < 10; i++) {
//            TravelOfficial travelOfficialData = new TravelOfficial();
//            travelOfficialData.setId((long) i);
//            travelOfficialData.setTitle("穿梭在云端的日子【蜜月之旅】");
//            travelOfficialData.setRegionId((long) 100);
//            travelOfficialData.setRegionName("西藏");
//            User user = new User();
//            user.setName("孙六");
//            user.setId((long) 2);
//            travelOfficialData.setUser(user);
//            travelOfficialData.setTravelStatus(1);
//            travelOfficialData.setPublishDate(new Date());
//            travelOfficialData.setPraiseNum(88);
//            travelOfficialData.setCollectionNum(880);
//        }
//        return null;
        TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
        if(StringUtils.isEmpty(travelOfficialListQuery.getRegionName())){

        }
        travelSpecialDTO.setPageSize(travelOfficialListQuery.getPageSize());
        travelSpecialDTO.setPageNo(travelOfficialListQuery.getPageNumber());
        BasePageResult<SnsTravelSpecialtyDO> result = snsCenterService.getTravelSpecialPage(travelSpecialDTO);

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
