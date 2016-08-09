package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.resourcecenter.domain.DestinationDO;

public class DestinationVO {
	
	private long id;
	private String name;
    
	public static List<DestinationVO> getDestinationVO(List<DestinationDO> DOList){
		
		List<DestinationVO> VOList = new ArrayList<DestinationVO>();
		if(DOList == null){
			return VOList;
		}
		
		DestinationVO destVO = null;
		DestinationDO destDO = null;
		for(int i = 0; i < DOList.size(); i++){
			
			destDO = DOList.get(i);
			destVO = new DestinationVO();
			destVO.setId(destDO.getId());
			destVO.setName(destDO.getName());
			VOList.add(destVO);
		}
		return VOList;
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
