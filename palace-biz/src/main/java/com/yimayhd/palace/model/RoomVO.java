package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.RoomFeature;
import com.yimayhd.ic.client.model.enums.RoomExtraBed;
import com.yimayhd.ic.client.model.enums.RoomNetwork;
import com.yimayhd.ic.client.model.enums.RoomWindow;

/**
 * Created by Administrator on 2015/11/20.
 */
public class RoomVO extends RoomDO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String bed;
	private double area;
	private String networkStr;
	private List<Integer> networkList;
	private int window;
	private int extraBed;
	private String floor;
	private int people;
	private String picListStr;

	public static RoomDO getRoomDO(RoomVO roomVO){
		
		RoomDO roomDO = new RoomDO();
		if(roomVO == null){
			return roomDO;
		}
		
        BeanUtils.copyProperties(roomVO, roomDO);
        
        RoomFeature feature = new RoomFeature();
        feature.setBed(roomVO.getBed());
        feature.setArea(roomVO.getArea());
        
        if(StringUtils.isNotBlank(roomVO.getNetworkStr())){
        	List<RoomNetwork> networkList = new ArrayList<RoomNetwork>();
            String[] networkArr = roomVO.getNetworkStr().split("\\|");
            for(int i = 0; i < networkArr.length; i++){
            	int net = Integer.parseInt(networkArr[i]);
            	
            	RoomNetwork[] values = RoomNetwork.values();
            	for(int j = 0; j < values.length; j++){
            		RoomNetwork value = values[j];
            		if(net == value.getType()){
                		networkList.add(value);
                	}
            	}
            }
            feature.setNetwork(networkList);
        }
        
        if(roomVO.getWindow() > 0){
        	RoomWindow[] values = RoomWindow.values();
        	for(int j = 0; j < values.length; j++){
        		RoomWindow value = values[j];
        		if(roomVO.getWindow() == value.getType()){
        			feature.setWindow(value);
        			break;
            	}
        	}
        }
        
        if(roomVO.getExtraBed() > 0){
        	RoomExtraBed[] values = RoomExtraBed.values();
        	for(int j = 0; j < values.length; j++){
        		RoomExtraBed value = values[j];
        		if(roomVO.getExtraBed() == value.getType()){
        			feature.setExtraBed(value);
        			break;
            	}
        	}
        }
            	
        feature.setFloor(roomVO.getFloor());
        feature.setPeople(roomVO.getPeople());
        
        roomDO.setFeature(feature);
        
        if(StringUtils.isNotBlank(roomVO.getPicListStr())){
        	List<String> pics = Arrays.asList(roomVO.getPicListStr().split("\\|"));
        	roomDO.setPics(pics);
        }
    	
        return roomDO;
    }
	
	public static RoomVO getRoomVO(RoomDO roomDO){
		
		RoomVO roomVO = new RoomVO();
		if(roomDO == null){
			return roomVO;
		}
		
        BeanUtils.copyProperties(roomDO, roomVO);
        
        RoomFeature feature = roomDO.getFeature();
        if(feature == null){
        	return roomVO;
        }
        
        
        roomVO.setBed(feature.getBed());
        if(feature.getArea() != null){
        	roomVO.setArea(feature.getArea());
        }
        
        roomVO.setNetworkList(feature.getNetwork());
        
        if(feature.getWindow() != null){
        	roomVO.setWindow(feature.getWindow());
        }
        
        if(feature.getExtraBed() != null){
        	roomVO.setExtraBed(feature.getExtraBed());
        }
        roomVO.setFloor(feature.getFloor());
        if(feature.getPeople() != null){
        	roomVO.setPeople(feature.getPeople());
        }
        
        return roomVO;
    }
	
	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}


	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		this.window = window;
	}

	public int getExtraBed() {
		return extraBed;
	}

	public void setExtraBed(int extraBed) {
		this.extraBed = extraBed;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}
	public String getNetworkStr() {
		return networkStr;
	}

	public void setNetworkStr(String networkStr) {
		this.networkStr = networkStr;
	}

	public List<Integer> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(List<Integer> networkList) {
		this.networkList = networkList;
	}
	
	public String getPicListStr() {
		return picListStr;
	}

	public void setPicListStr(String picListStr) {
		this.picListStr = picListStr;
	}
}
