package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HotelVO extends HotelDO {

    private MasterRecommend masterRecommend;
    private List<String> phoneNumList;
    private List<String> picturesList;

    public HotelVO(){

    }

    public static HotelVO getHotelVO(HotelDO hotelDO){
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelDO,hotelVO);
        //个性化转换
        hotelVO.setMasterRecommend(JSON.parseObject(hotelDO.getRecommend(), MasterRecommend.class));
        hotelVO.setPhoneNumList(JSON.parseArray(hotelDO.getPhoneNum(), String.class));
        hotelVO.setPicturesList(JSON.parseArray(hotelDO.getPictures(), String.class));
        return hotelVO;
    }
    public static HotelDO getHotelDO(HotelVO hotelVO){
        HotelDO hotelDO = hotelVO;
        //个性化转换
        hotelDO.setRecommend(JSON.toJSONString(hotelVO.getMasterRecommend()));
        hotelDO.setPhoneNum(JSON.toJSONString(hotelVO.getPhoneNumList()));
        hotelDO.setPictures(JSON.toJSONString(hotelVO.getPicturesList()));
        return hotelDO;
    }

    public MasterRecommend getMasterRecommend() {
        return masterRecommend;
    }

    public void setMasterRecommend(MasterRecommend masterRecommend) {
        this.masterRecommend = masterRecommend;
    }

    public List<String> getPhoneNumList() {
        return phoneNumList;
    }

    public void setPhoneNumList(List<String> phoneNumList) {
        this.phoneNumList = phoneNumList;
    }

    public List<String> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<String> picturesList) {
        this.picturesList = picturesList;
    }
    //TODO 例子，后期需要删除
    public static void main(String[] args) {
        HotelDO hotelDOData = new HotelDO();
        hotelDOData.setId(30);
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
        hotelDOData.setStatus(1);
        hotelDOData.setPrice(5600);
        hotelDOData.setOpenTime("[8,12,20]");
        hotelDOData.setContactPerson("张三");
        hotelDOData.setContactPhone("18039262076");
        hotelDOData.setLogoUrl("logo.png");
        hotelDOData.setCoverUrl("main.png");
        hotelDOData.setRecommend("{\"name\":\"tini\",\"description\":\"很棒的酒店\",\"id\":1,\"user_id\":2}");
        HotelVO hotelVO = getHotelVO(hotelDOData);
        System.out.println(hotelVO.getLocationCityName());
        System.out.println(hotelVO.getMasterRecommend().getName() + "-" + hotelVO.getMasterRecommend().getDescription());
        System.out.println(hotelVO.getPhoneNumList().get(1));
        HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
        System.out.println(hotelDO.getRecommend());
        System.out.println(hotelDO.getPhoneNum());
    }
}
