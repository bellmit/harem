package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.vo.HotelVO;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.ic.client.model.domain.HotelDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelServiceImpl implements HotelService {
    @Override
    public List<HotelDO> getList(HotelVO hotelVO) throws Exception {
        List<HotelDO> hotelDOList = new ArrayList<HotelDO>();
        for (int i = 0; i < 10; i++) {
            HotelDO hotelDOData = new HotelDO();
            hotelDOData.setId(i);
            hotelDOData.setName("昆明七彩云南温德姆至尊豪廷大酒店");
            hotelDOData.setDescription("至尊豪");
            hotelDOData.setPhoneNum("18618161816");
            hotelDOData.setType(1);
            hotelDOData.setGrade(4);
            hotelDOData.setRecommend("希望更豪华");
            hotelDOData.setRoomFacility("1,2,3");
            hotelDOData.setRoomService("2,3");
            hotelDOData.setHotelFacility("1,2,3");
            hotelDOData.setLogoUrl("123.png");
            hotelDOData.setPictures("123.png,2.png,3.png");
            hotelDOData.setLocationX(1000);
            hotelDOData.setLocationY(2000);
            hotelDOData.setLocationText("昆明湖");
            hotelDOData.setLocationProvinceId(1);
            hotelDOData.setLocationProvinceName("云南");
            hotelDOData.setLocationCityId(11);
            hotelDOData.setLocationCityName("昆明");
            hotelDOData.setLocationTownId(111);
            hotelDOData.setLocationTownName("海淀区");
            hotelDOData.setGmtCreated(new Date());
            hotelDOData.setGmtModified(new Date());
            hotelDOData.setStatus(1);
            hotelDOList.add(hotelDOData);
        }
        return hotelDOList;
    }

    @Override
    public HotelDO getById(long id) throws Exception {
        HotelDO hotelDOData = new HotelDO();
        hotelDOData.setId(30);
        hotelDOData.setName("昆明七彩云南温德姆至尊豪廷大酒店");
        hotelDOData.setDescription("至尊豪");
        hotelDOData.setPhoneNum("18618161816");
        hotelDOData.setType(1);
        hotelDOData.setGrade(4);
        hotelDOData.setRecommend("希望更豪华");
        hotelDOData.setRoomFacility("1,2,3");
        hotelDOData.setRoomService("2,3");
        hotelDOData.setHotelFacility("1,2,3");
        hotelDOData.setLogoUrl("123.png");
        hotelDOData.setPictures("123.png,2.png,3.png");
        hotelDOData.setLocationX(1000);
        hotelDOData.setLocationY(2000);
        hotelDOData.setLocationText("昆明湖");
        hotelDOData.setLocationProvinceId(1);
        hotelDOData.setLocationProvinceName("云南");
        hotelDOData.setLocationCityId(11);
        hotelDOData.setLocationCityName("昆明");
        hotelDOData.setLocationTownId(111);
        hotelDOData.setLocationTownName("海淀区");
        hotelDOData.setGmtCreated(new Date());
        hotelDOData.setGmtModified(new Date());
        return hotelDOData;
    }

    @Override
    public HotelDO add(HotelDO hotelDO) throws Exception {
        return null;
    }

    @Override
    public void modify(HotelDO hotelDO) throws Exception {

    }

    @Override
    public void setHotelStatusList(List<Integer> idList, int hotelStatus) throws Exception {

    }

    @Override
    public void setHotelStatus(long id, int hotelStatus) throws Exception {


    }
}
