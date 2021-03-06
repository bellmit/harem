package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;


/**
 * Created by hongfei.guo on 2015/12/25.
 */
public class ScenicAddVO {
    
	private ScenicVO scenicVO;
	private List<TicketVO> ticketVOList;
	
	private String insertTicketListStr;
	
	private String updateTicketListStr;
	
	private PictureTextVO	pictureText; // 图文详情
		
	public static List<TicketDO> transformTicketDOList(String ticketListStr){
		if(StringUtils.isBlank(ticketListStr) || ticketListStr.equals("[]")){
			return null;
		}
		
		List<TicketDO> list = new ArrayList<TicketDO>();
		if(StringUtils.isNotBlank(ticketListStr)) {
	        list = JSON.parseArray(ticketListStr, TicketDO.class);
	    }
		return list;
	}
	
	public static List<TicketVO> transformTicketVOList(List<TicketDO> ticketDOList){
		
		if(ticketDOList == null){
			return null;
		}
		
		List<TicketVO> list = new ArrayList<TicketVO>();
		
		TicketDO ticketDO = null;
		for(int i = 0; i < ticketDOList.size(); i++){
			ticketDO = ticketDOList.get(i);
			list.add(TicketVO.getScenicVO(ticketDO));
		}
		return list;
	}
	
	public static List<TicketVO> transFormToTicketVOList(String ticketListStr){
		
		List<TicketVO> list = new ArrayList<TicketVO>();
		if(StringUtils.isNotBlank(ticketListStr)) {
	        list = JSON.parseArray(ticketListStr, TicketVO.class);
	    }
		return list;
	}
	
	public ScenicVO getScenicVO() {
		return scenicVO;
	}
	public void setScenicVO(ScenicVO scenicVO) {
		this.scenicVO = scenicVO;
	}
	public List<TicketVO> getTicketVOList() {
		return ticketVOList;
	}
	public void setTicketVOList(List<TicketVO> ticketVOList) {
		this.ticketVOList = ticketVOList;
	}
	public PictureTextVO getPictureText() {
		return pictureText;
	}
	public void setPictureText(PictureTextVO pictureText) {
		this.pictureText = pictureText;
	}
	public String getInsertTicketListStr() {
		return insertTicketListStr;
	}

	public void setInsertTicketListStr(String insertTicketListStr) {
		this.insertTicketListStr = insertTicketListStr;
	}

	public String getUpdateTicketListStr() {
		return updateTicketListStr;
	}

	public void setUpdateTicketListStr(String updateTicketListStr) {
		this.updateTicketListStr = updateTicketListStr;
	}
}
