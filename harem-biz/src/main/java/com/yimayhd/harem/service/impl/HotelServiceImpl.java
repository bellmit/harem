package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
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
    public List<HotelDO> getList(HotelListQuery hotelListQuery) throws Exception {
        List<HotelDO> hotelDOList = new ArrayList<HotelDO>();
        for (int i = 0; i < 10; i++) {
            HotelDO hotelDOData = new HotelDO();
            hotelDOData.setId(i);
            hotelDOData.setName("温德姆至尊豪廷大酒店");
            hotelDOData.setOneword("是个不错的酒店");
            hotelDOData.setDescription("至尊豪，真的是个不错的酒店");
            hotelDOData.setPhoneNum("[\"18618161816\",\"18618161817\",\"18618161819\"]");
            hotelDOData.setLevel(4);
            hotelDOData.setRecommend("希望更豪华");
            //hotelDOData.setRoomFacility("1,2,3");
            //hotelDOData.setRoomService("2,3");
            //hotelDOData.setHotelFacility("1,2,3");
            hotelDOData.setLogoUrl("123.png");
            hotelDOData.setPictures("[\"123.png\",\"2.png\",\"3.png\"]");
            hotelDOData.setLocationX(1000.555);
            hotelDOData.setLocationY(2000.666);
            hotelDOData.setLocationText("海淀区");
            hotelDOData.setLocationProvinceId(1);
            hotelDOData.setLocationProvinceName("云南");
            hotelDOData.setLocationCityId(11);
            hotelDOData.setLocationCityName("昆明");
            hotelDOData.setGmtCreated(new Date());
            hotelDOData.setGmtModified(new Date());
            hotelDOData.setStatus(1);
            hotelDOData.setPrice(5600);
            hotelDOData.setContactPerson("张三");
            hotelDOData.setContactPhone("18039262076");
            hotelDOData.setLogoUrl("logo.png");
            hotelDOData.setCoverUrl("main.png");
            hotelDOList.add(hotelDOData);
        }
        return hotelDOList;
    }

    @Override
    public HotelVO getById(long id) throws Exception {
        HotelDO hotelDOData = new HotelDO();
        hotelDOData.setId(30);
        hotelDOData.setName("温德姆至尊豪廷大酒店");
        hotelDOData.setOneword("是个不错的酒店");
        hotelDOData.setDescription("至尊豪，真的是个不错的酒店");
        hotelDOData.setPhoneNum("[\"18618161816\",\"18618161817\",\"18618161819\"]");
        hotelDOData.setLevel(2);
        hotelDOData.setRecommend("希望更豪华");
        //hotelDOData.setRoomFacility("1,2,3");
        //hotelDOData.setRoomService("2,3");
        //hotelDOData.setHotelFacility("1,2,3");
        hotelDOData.setLogoUrl("123.png");
        hotelDOData.setPictures("[\"123.png\",\"2.png\",\"3.png\"]");
        hotelDOData.setLocationX(1000.555);
        hotelDOData.setLocationY(2000.666);
        hotelDOData.setLocationText("海淀区");
        hotelDOData.setLocationProvinceId(10);
        hotelDOData.setLocationProvinceName("云南");
        hotelDOData.setLocationCityId(100);
        hotelDOData.setLocationCityName("昆明");
        hotelDOData.setGmtCreated(new Date());
        hotelDOData.setGmtModified(new Date());
        hotelDOData.setStatus(1);
        hotelDOData.setPrice(5600);
        hotelDOData.setOpenTime("[8,12,20]");
        hotelDOData.setContactPerson("张三");
        hotelDOData.setContactPhone("18039262076");
        hotelDOData.setLogoUrl("logo.png");
        hotelDOData.setCoverUrl("main.png");
        hotelDOData.setRecommend("{\"name\":\"tini\",\"description\":\"很棒的酒店\",\"id\":1,\"user_id\":2,\"user_pic\":\"pic.png\"}");
        return HotelVO.getHotelVO(hotelDOData);
    }

    @Override
    public HotelDO add(HotelVO hotelVO) throws Exception {
        return null;
    }

    @Override
    public void modify(HotelVO hotelVO) throws Exception {

    }
}
