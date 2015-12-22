package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.service.impl.FacilityIconServiceImpl;
import com.yimayhd.harem.util.BitUtil;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HotelVO extends HotelDO implements Serializable {

    private static final long serialVersionUID = 6419972389326909198L;
    private MasterRecommend masterRecommend;
    private List<String> phoneNumList;
    private List<Picture> pictureList;
    private List<String> openTimeList;
    private List<String> roomFacilityList;
    private List<String> roomServiceList;
    private List<String> hotelFacilityList;
    public HotelVO(){

    }
    public static HotelVO getHotelVO(HotelDO hotelDO) throws Exception {
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelDO, hotelVO);
        //个性化转换
        hotelVO.setMasterRecommend(JSON.parseObject(hotelDO.getRecommend(), MasterRecommend.class));
        //hotelVO.setPhoneNumList(JSON.parseArray(hotelDO.getPhoneNum(), String.class));
        //hotelVO.setPictureList(JSON.parseArray(hotelDO.getPictures(), Picture.class));
        //hotelVO.setOpenTimeList(JSON.parseArray(hotelDO.getOpenTime(), String.class));
        //FacilityIconServiceImpl facilityIconService = new FacilityIconServiceImpl();
        //hotelVO.setRoomFacilityList(BitUtil.convertFacility(hotelDO.getRoomFacility(),facilityIconService.getMapByType(1),0));
        //hotelVO.setRoomServiceList(BitUtil.convertFacility(hotelDO.getRoomService(), facilityIconService.getMapByType(2), 0));
        //hotelVO.setHotelFacilityList(BitUtil.convertFacility(hotelDO.getHotelFacility(), facilityIconService.getMapByType(3), 0));
        return hotelVO;
    }
    public static HotelDO getHotelDO(HotelVO hotelVO) throws Exception {
        HotelDO hotelDO = hotelVO;
        //个性化转换
        //hotelDO.setRecommend(JSON.toJSONString(hotelVO.getMasterRecommend()));
        hotelDO.setPhoneNum(JSON.toJSONString(hotelVO.getPhoneNumList()));
        hotelDO.setPictures(JSON.toJSONString(hotelVO.getPictureList()));
        hotelDO.setOpenTime(JSON.toJSONString(hotelVO.getOpenTimeList()));
        //hotelDO.setRoomFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setRoomService(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setHotelFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public List<String> getOpenTimeList() {
        return openTimeList;
    }

    public void setOpenTimeList(List<String> openTimeList) {
        this.openTimeList = openTimeList;
    }

    public List<String> getRoomFacilityList() {
        return roomFacilityList;
    }

    public void setRoomFacilityList(List<String> roomFacilityList) {
        this.roomFacilityList = roomFacilityList;
    }

    public List<String> getRoomServiceList() {
        return roomServiceList;
    }

    public void setRoomServiceList(List<String> roomServiceList) {
        this.roomServiceList = roomServiceList;
    }

    public List<String> getHotelFacilityList() {
        return hotelFacilityList;
    }

    public void setHotelFacilityList(List<String> hotelFacilityList) {
        this.hotelFacilityList = hotelFacilityList;
    }

    //TODO 例子，后期需要删除
    public static void main(String[] args) throws Exception {
        HotelDO hotelDOData = new HotelDO();
        hotelDOData.setId(30);
        hotelDOData.setName("温德姆至尊豪廷大酒店");
        hotelDOData.setOneword("是个不错的酒店");
        hotelDOData.setDescription("至尊豪，真的是个不错的酒店");
        hotelDOData.setPhoneNum("[\"18618161816\",\"18618161817\",\"18618161819\"]");
        hotelDOData.setLevel(4);
        hotelDOData.setRecommend("希望更豪华");
        hotelDOData.setRoomFacility(5);
        hotelDOData.setRoomService(6);
        hotelDOData.setHotelFacility(3);
        hotelDOData.setLogoUrl("123.png");
        hotelDOData.setPictures("[{\"url\":\"1.png\",\"top\":1,\"name\":\"1\"},{\"url\":\"2.png\",\"top\":1,\"name\":\"2\"},{\"url\":\"3.png\",\"top\":1,\"name\":\"3\"},{\"url\":\"4.png\",\"top\":0,\"name\":\"4\"}]");
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
        List<String> idList = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            idList.add(String.valueOf(1));
        }
        hotelVO.setRoomFacilityList(idList);
        hotelVO.setRoomServiceList(idList);
        hotelVO.setHotelFacilityList(idList);

        HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
        System.out.println(hotelDO.getRecommend());
        System.out.println(hotelDO.getPhoneNum());
    }


}
