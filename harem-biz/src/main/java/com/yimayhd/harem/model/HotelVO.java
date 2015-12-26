package com.yimayhd.harem.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.util.NumUtil;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HotelVO extends HotelDO implements Serializable {

    private static final long serialVersionUID = 6419972389326909198L;
    private MasterRecommend masterRecommend;
    private List<String> phoneNumList;
    private String picturesStr;//列表长图
    private String phoneNumListStr;//以逗号分割的电话列表
    private List<PictureVO> pictureList;//图片集
    private String picListStr;//图片集的str
    private List<String> openTimeList;
    private List<String> roomFacilityList;
    private List<String> roomServiceList;
    private List<String> hotelFacilityList;
    private double priceY;//价格元

    private String roomFacilityStr;
    private String roomServiceStr;
    private String hotelFacilityStr;
    private NeedKnow needKnowOb;
    private String name2;
    public HotelVO(){

    }
    public static HotelVO getHotelVO(HotelDO hotelDO) throws Exception {
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelDO, hotelVO);
        //个性化转换
        //MasterRecommend
        hotelVO.setMasterRecommend(hotelDO.getRecommend());
        //分转元
        hotelVO.setPriceY(NumUtil.moneyTransformDouble(hotelDO.getPrice()));
        //电话号码处理
        List<String> phoneListStr = hotelDO.getPhoneNum();
        if(CollectionUtils.isNotEmpty(phoneListStr)){
            String phoneNumListStr = "";
            for(String str : phoneListStr){
                if("".equals(phoneNumListStr)){
                    phoneNumListStr += str;
                }else{
                    phoneNumListStr += "," + str;
                }
            }
            hotelVO.setPhoneNumListStr(phoneNumListStr);
        }
        
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
        long roomFacility = Long.parseLong(new StringBuilder(hotelVO.getRoomFacilityStr()).reverse().toString(), 2);
        long roomService = Long.parseLong(new StringBuilder(hotelVO.getRoomServiceStr()).reverse().toString(), 2);
        long hotelFacility = Long.parseLong(new StringBuilder(hotelVO.getHotelFacilityStr()).reverse().toString(), 2);

        hotelVO.getMasterRecommend().setName(hotelVO.getName2());
        hotelVO.setNeedKnow(hotelVO.getNeedKnowOb());
        hotelVO.setRecommend(hotelVO.getMasterRecommend());
        hotelVO.setRoomFacility(roomFacility);
        hotelVO.setRoomService(roomService);
        hotelVO.setHotelFacility(hotelFacility);
        hotelVO.setStatus(BaseStatus.DELETED.getType());
        //电话处理
        if(StringUtils.isNotBlank(hotelVO.getPhoneNumListStr())){
            List<String> phoneNumList = Arrays.asList(hotelVO.getPhoneNumListStr().split(","));
            hotelDO.setPhoneNum(phoneNumList);
        }
        //图片集处理(因为有outId还是,只处理新增的)
        //列表长图片处理
        hotelDO.setPicturesString(hotelVO.getPicturesStr());
        hotelDO.setOpenTime(JSON.toJSONString(hotelVO.getOpenTimeList()));
        //hotelDO.setRoomFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setRoomService(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setHotelFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //元转分
        hotelDO.setPrice((long) (hotelVO.getPriceY() * 100));
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

    public List<PictureVO> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureVO> pictureList) {
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

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }

    public String getPhoneNumListStr() {
        return phoneNumListStr;
    }

    public void setPhoneNumListStr(String phoneNumListStr) {
        this.phoneNumListStr = phoneNumListStr;
    }

    public String getPicListStr() {
        return picListStr;
    }

    public void setPicListStr(String picListStr) {
        this.picListStr = picListStr;
    }

    public String getPicturesStr() {
        return picturesStr;
    }

    public void setPicturesStr(String picturesStr) {
        this.picturesStr = picturesStr;
    }

    public String getRoomFacilityStr() {
        return roomFacilityStr;
    }

    public void setRoomFacilityStr(String roomFacilityStr) {
        this.roomFacilityStr = roomFacilityStr;
    }

    public String getRoomServiceStr() {
        return roomServiceStr;
    }

    public void setRoomServiceStr(String roomServiceStr) {
        this.roomServiceStr = roomServiceStr;
    }

    public String getHotelFacilityStr() {
        return hotelFacilityStr;
    }

    public void setHotelFacilityStr(String hotelFacilityStr) {
        this.hotelFacilityStr = hotelFacilityStr;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public NeedKnow getNeedKnowOb() {
        return needKnowOb;
    }

    public void setNeedKnowOb(NeedKnow needKnowOb) {
        this.needKnowOb = needKnowOb;
    }
}
