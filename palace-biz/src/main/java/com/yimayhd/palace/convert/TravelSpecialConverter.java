package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.palace.model.travel.SnsTravelSpecialtyVO;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.user.client.domain.UserDO;

public class TravelSpecialConverter {

	public static List<SnsTravelSpecialtyVO> TravelSpecialtyDOToVoConveter(List<SnsTravelSpecialtyDO> specialDoList,Map<Long, UserDO> map){
		List<SnsTravelSpecialtyVO> list = new ArrayList<SnsTravelSpecialtyVO>();
		if(CollectionUtils.isEmpty(specialDoList) || map.size() == 0){
			return list;
		}
		for(SnsTravelSpecialtyDO snsTravelSpecialtyDO : specialDoList){
			SnsTravelSpecialtyVO specialtyVO = TravelSpecialConverter.getBySpecialtyDO(snsTravelSpecialtyDO);
			UserDO userDO = map.get(snsTravelSpecialtyDO.getCreateId());
			if( null != userDO ){
				specialtyVO.setNickName(map.get(snsTravelSpecialtyDO.getCreateId()).getNickname());
			}
			list.add(specialtyVO);
		}
		return list;
	}
	
	private static SnsTravelSpecialtyVO getBySpecialtyDO(SnsTravelSpecialtyDO snsTravelSpecialtyDO){
		SnsTravelSpecialtyVO snsTravelSpecialtyVO = new SnsTravelSpecialtyVO();
		if( null == snsTravelSpecialtyDO ){
			return snsTravelSpecialtyVO;
		}
		snsTravelSpecialtyVO.setBackImg(snsTravelSpecialtyDO.getBackImg());
		snsTravelSpecialtyVO.setCreateId(snsTravelSpecialtyDO.getCreateId());
		//snsTravelSpecialtyVO.setDomain(snsTravelSpecialtyDO.getDomain());
		snsTravelSpecialtyVO.setGmtCreated(snsTravelSpecialtyDO.getGmtCreated());
		snsTravelSpecialtyVO.setGmtModified(snsTravelSpecialtyDO.getGmtCreated());
		snsTravelSpecialtyVO.setId(snsTravelSpecialtyDO.getId());
		snsTravelSpecialtyVO.setImgContentJson(snsTravelSpecialtyDO.getImgContentJson());
		snsTravelSpecialtyVO.setPreface(snsTravelSpecialtyDO.getPreface());
		snsTravelSpecialtyVO.setPoiContent(snsTravelSpecialtyDO.getPoiContent());
		snsTravelSpecialtyVO.setStatus(snsTravelSpecialtyDO.getStatus());
		snsTravelSpecialtyVO.setTitle(snsTravelSpecialtyDO.getTitle());
		snsTravelSpecialtyVO.setTravelJsonDO(snsTravelSpecialtyDO.getTravelJsonDO());
		return snsTravelSpecialtyVO;
	}
}
