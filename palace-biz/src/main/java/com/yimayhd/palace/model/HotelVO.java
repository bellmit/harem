package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.item.HotelFeature;
import com.yimayhd.ic.client.model.domain.item.TradeArea;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.enums.PictureTag;
import com.yimayhd.ic.client.model.param.item.HotelDTO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.util.Common;
import com.yimayhd.palace.util.NumUtil;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HotelVO extends HotelDO implements Serializable {

    private static final long serialVersionUID = 6419972389326909198L;
    private List<String> phoneNumList;
    private String picturesStr;//列表长图
    private String phoneNumListStr;//以逗号分割的电话列表
    private List<PictureVO> pictureList;//图片集
    private String picListStr;//图片集的str
    private List<String> openTimeList;//暂时没有用到
    private List<String> roomFacilityList;
    private List<String> roomServiceList;
    private List<String> hotelFacilityList;
    private double priceY;//价格元

    private String roomFacilityStr;
    private String roomServiceStr;
    private String hotelFacilityStr;

    private String needKnowFrontNeedKnowStr;//须知json字符串
    
	private String outlookPicListStr;//外观图片的str
	private String inDoorPicListStr;//内景图片的str
    private String roomPicListStr;//房间图片的str
    private String otherPicListStr;//其他图片的str
    
    private List<PictureVO> outlookPicList;//外观
	private List<PictureVO> inDoorPicList;//内景
    private List<PictureVO> roomPicList;//房间
    private List<PictureVO> otherPicList;//其他
    
    private String tradeAreaJson;
	private List<TradeArea> tradeAreaList;
	private PictureTextVO	pictureText; // 图文详情
  
	public HotelVO(){

    }
    public static HotelVO getHotelVO(HotelDO hotelDO) throws Exception {
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotelDO, hotelVO);
        //个性化转换
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
        //needKonw(extraInfoUrl是富文本编辑，service中处理)
        if(hotelVO.getNeedKnow() == null){
            hotelVO.setNeedKnow(new NeedKnow());
        }
        if(StringUtils.isNotBlank(hotelVO.getNeedKnowFrontNeedKnowStr())){
            List<TextItem> frontNeedKnow = JSON.parseArray(hotelVO.getNeedKnowFrontNeedKnowStr(),TextItem.class);
            hotelVO.getNeedKnow().setFrontNeedKnow(frontNeedKnow);
        }
        
        Coordinate cdt = Common.gcjToBd(hotelVO.getLocationY(), hotelVO.getLocationX());
        hotelVO.setLongitude(cdt.getLongitude());
        hotelVO.setLatitude(cdt.getLatitude());
        
        return hotelVO;
    }
    public static HotelDO getHotelDO(HotelVO hotelVO) throws Exception {
        HotelDO hotelDO = hotelVO;
        //个性化转换
        //hotelDO.setRecommend(JSON.toJSONString(hotelVO.getMasterRecommend()));
        long roomFacility = Long.parseLong(new StringBuilder(hotelVO.getRoomFacilityStr()).reverse().toString(), 2);
        long roomService = Long.parseLong(new StringBuilder(hotelVO.getRoomServiceStr()).reverse().toString(), 2);
        long hotelFacility = Long.parseLong(new StringBuilder(hotelVO.getHotelFacilityStr()).reverse().toString(), 2);

        //needKonw(extraInfoUrl是富文本编辑，service中处理)
        if(hotelVO.getNeedKnow() == null){
            hotelVO.setNeedKnow(new NeedKnow());
        }
        if(StringUtils.isNotBlank(hotelVO.getNeedKnowFrontNeedKnowStr())){
            List<TextItem> frontNeedKnow = JSON.parseArray(hotelVO.getNeedKnowFrontNeedKnowStr(),TextItem.class);
            hotelVO.getNeedKnow().setFrontNeedKnow(frontNeedKnow);
        }
        
        hotelVO.setRoomFacility(roomFacility);
        hotelVO.setRoomService(roomService);
        hotelVO.setHotelFacility(hotelFacility);
        
        /*
        HotelFeature hotelFeature = new HotelFeature();
        hotelFeature.setRoomFacility(roomFacility);
        hotelFeature.setHotelService(roomService);
        hotelFeature.setHotelFacility(hotelFacility);
        hotelVO.setFeature(hotelFeature);
         */
        
        //电话处理
        if(StringUtils.isNotBlank(hotelVO.getPhoneNumListStr())){
            List<String> phoneNumList = Arrays.asList(hotelVO.getPhoneNumListStr().split(","));
            hotelDO.setPhoneNum(phoneNumList);
        }
        //大咖推荐不必填
        if(hotelVO.getRecommend() != null && 0 == hotelVO.getRecommend().getId()){
            hotelDO.setRecommend(null);
        }
        //图片集处理(因为有outId还是,只处理新增的)
        //列表长图片处理
        hotelDO.setPicturesString(hotelVO.getPicturesStr());

        //hotelDO.setOpenTime(JSON.toJSONString(hotelVO.getOpenTimeList()));//TODO 暂时变更
        //hotelDO.setRoomFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setRoomService(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //hotelDO.setHotelFacility(BitUtil.convertLong(hotelVO.getRoomFacilityList(), 0));
        //元转分
        hotelDO.setPrice(NumUtil.doubleToLong(hotelVO.getPriceY()));
        return hotelDO;
    }
    
    public static HotelDO getHotelDOV2(HotelVO hotelVO) throws Exception {
        HotelDO hotelDO = hotelVO;

        List<Integer> roomFacility = transformFacilities(hotelVO.getRoomFacilityStr());
        List<Integer> roomService = transformFacilities(hotelVO.getRoomServiceStr()); 
        List<Integer> hotelFacility = transformFacilities(hotelVO.getHotelFacilityStr());

        HotelFeature hotelFeature = new HotelFeature();
        hotelFeature.setRoomFacility(roomFacility);
        hotelFeature.setHotelService(roomService);
        hotelFeature.setHotelFacility(hotelFacility);
        hotelVO.setFeature(hotelFeature);
        
        String tradeAreaJson = hotelVO.getTradeAreaJson(); 
        if(StringUtils.isNotBlank(tradeAreaJson) && !tradeAreaJson.equals("[]")){
            List<TradeArea> areaList = JSONArray.parseArray(hotelVO.getTradeAreaJson(), TradeArea.class);
            hotelFeature.setTradeArea(areaList);
        }
        
        //电话处理
        if(StringUtils.isNotBlank(hotelVO.getPhoneNumListStr())){
            List<String> phoneNumList = Arrays.asList(hotelVO.getPhoneNumListStr().split(","));
            hotelDO.setPhoneNum(phoneNumList);
        }
       
        //列表长图片处理
        hotelDO.setPicturesString(hotelVO.getPicturesStr());
        
        //处理经纬度
        //hotelDO.setLatitude(hotelVO.getLocationY());
        //hotelDO.setLongitude(hotelVO.getLocationX());
        
        Coordinate cdt = Common.bdToGcj(hotelVO.getLocationY(), hotelVO.getLocationX());
        hotelDO.setLongitude(cdt.getLongitude());
        hotelDO.setLatitude(cdt.getLatitude());
        
        return hotelDO;
    }
    
    public static List<Integer> transformFacilities(String roomFacilityStr){
    	
    	List<Integer> list = new ArrayList<Integer>();
        if(StringUtils.isBlank(roomFacilityStr)){
        	return list;
        }
        
    	String[] arr = roomFacilityStr.split("\\|");
    	for(int i = 0; i < arr.length; i++){
    		list.add(Integer.parseInt(arr[i]));
    	}
    	return list;
    }
    
    public static HotelDTO getHotelDTO(HotelVO hotelVO) throws Exception {
    	
    	HotelDTO hotelDTO = new HotelDTO();
    	BeanUtils.copyProperties(hotelVO, hotelDTO);
    	
    	//hotelDTO.setLatitude(hotelVO.getLocationY());
        //hotelDTO.setLongitude(hotelVO.getLocationX());
    	
    	Coordinate cdt = Common.bdToGcj(hotelVO.getLocationY(), hotelVO.getLocationX());
    	hotelDTO.setLongitude(cdt.getLongitude());
        hotelDTO.setLatitude(cdt.getLatitude());
        
        List<Integer> roomFacility = transformFacilities(hotelVO.getRoomFacilityStr());
        List<Integer> roomService = transformFacilities(hotelVO.getRoomServiceStr()); 
        List<Integer> hotelFacility = transformFacilities(hotelVO.getHotelFacilityStr());
    	
    	HotelFeature feature = new HotelFeature();
    	feature.setRoomFacility(roomFacility);
    	feature.setHotelService(roomService);
    	feature.setHotelFacility(hotelFacility);
        hotelDTO.setFeature(feature);
        
        String tradeAreaJson = hotelVO.getTradeAreaJson(); 
        if(StringUtils.isNotBlank(tradeAreaJson) && !tradeAreaJson.equals("[]")){
            List<TradeArea> areaList = JSONArray.parseArray(hotelVO.getTradeAreaJson(), TradeArea.class);
            feature.setTradeArea(areaList);
        }
        
        String phoneNumListStr = hotelVO.getPhoneNumListStr();
        if(StringUtils.isNotBlank(phoneNumListStr) && !phoneNumListStr.equals("[]")){
        	List<String> phoneNumList = Arrays.asList(hotelVO.getPhoneNumListStr().split(","));
        	hotelDTO.setPhoneNum(phoneNumList);
        }
        
        return hotelDTO;
    }
    
    public static List<PictureVO> transFromToPicturesList(HotelVO hotelVO) throws Exception {
    	
    	List<PictureVO> allList = new ArrayList<PictureVO>();
    	
    	List<PictureVO> partList = transFromToPicturesList(hotelVO.getOutlookPicListStr(), PictureTag.OUTLOOK.getValue());
    	allList.addAll(partList);
    	
		partList = transFromToPicturesList(hotelVO.getInDoorPicListStr(), PictureTag.INDOOR.getValue());
    	allList.addAll(partList);
	
		partList = transFromToPicturesList(hotelVO.getRoomPicListStr(), PictureTag.ROOM.getValue());
    	allList.addAll(partList);
	
		partList = transFromToPicturesList(hotelVO.getOtherPicListStr(), PictureTag.OTHER.getValue());
    	allList.addAll(partList);
    	
    	return allList;
    }
    
    public static List<PictureVO> transFromToPicturesList(String picListStr, int picTag) throws Exception {
    	
    	List<PictureVO> list = new ArrayList<PictureVO>();
    	if(StringUtils.isBlank(picListStr)){
    		return list;
    	}
    	
		list = JSON.parseArray(picListStr, PictureVO.class);
		for(int i = 0; i < list.size(); i++){
			list.get(i).setTag(picTag);
		}
    	return list;
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

    public String getNeedKnowFrontNeedKnowStr() {
        return needKnowFrontNeedKnowStr;
    }

    public void setNeedKnowFrontNeedKnowStr(String needKnowFrontNeedKnowStr) {
        this.needKnowFrontNeedKnowStr = needKnowFrontNeedKnowStr;
    }
    public String getOutlookPicListStr() {
		return outlookPicListStr;
	}
	public void setOutlookPicListStr(String outlookPicListStr) {
		this.outlookPicListStr = outlookPicListStr;
	}
	public String getInDoorPicListStr() {
		return inDoorPicListStr;
	}
	public void setInDoorPicListStr(String inDoorPicListStr) {
		this.inDoorPicListStr = inDoorPicListStr;
	}
	public String getRoomPicListStr() {
		return roomPicListStr;
	}
	public void setRoomPicListStr(String roomPicListStr) {
		this.roomPicListStr = roomPicListStr;
	}
	public String getOtherPicListStr() {
		return otherPicListStr;
	}
	public void setOtherPicListStr(String otherPicListStr) {
		this.otherPicListStr = otherPicListStr;
	}
	public List<PictureVO> getOutlookPicList() {
		return outlookPicList;
	}
	public void setOutlookPicList(List<PictureVO> outlookPicList) {
		this.outlookPicList = outlookPicList;
	}
	public List<PictureVO> getInDoorPicList() {
		return inDoorPicList;
	}
	public void setInDoorPicList(List<PictureVO> inDoorPicList) {
		this.inDoorPicList = inDoorPicList;
	}
	public List<PictureVO> getRoomPicList() {
		return roomPicList;
	}
	public void setRoomPicList(List<PictureVO> roomPicList) {
		this.roomPicList = roomPicList;
	}
	public List<PictureVO> getOtherPicList() {
		return otherPicList;
	}
	public void setOtherPicList(List<PictureVO> otherPicList) {
		this.otherPicList = otherPicList;
	}
	public PictureTextVO getPictureText() {
		return pictureText;
	}
	public void setPictureText(PictureTextVO pictureText) {
		this.pictureText = pictureText;
	}
	public String getTradeAreaJson() {
		return tradeAreaJson;
	}
	public void setTradeAreaJson(String tradeAreaJson) {
		this.tradeAreaJson = tradeAreaJson;
	}
	public List<TradeArea> getTradeAreaList() {
		return tradeAreaList;
	}
	public void setTradeAreaList(List<TradeArea> tradeAreaList) {
		this.tradeAreaList = tradeAreaList;
	}
}
