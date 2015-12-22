package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.service.RecommendedService;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;

public class RecommendedServiceImpl implements RecommendedService {

	@Autowired RegionIntroduceClientService RegionIntroduceClientServiceRef;
	
	public boolean saveOrUpdate(RegionIntroduceDO regionIntroduce) throws Exception {
		if(null == regionIntroduce){
			throw new Exception("parameters [regionIntroduceDO] cannot be empty,RegionIntroduceDO="+JSON.toJSONString(regionIntroduce));
		}
		/*RegionIntroduceDO regionIntroduceDO = new RegionIntroduceDO();
		regionIntroduceDO.setCityCode(regionIntroduce.getCityCode());
		regionIntroduceDO.setContent(regionIntroduce.getContent());
		regionIntroduceDO.setDesc(regionIntroduce.getDesc());
		regionIntroduceDO.setGmtCreate(new Date());
		regionIntroduceDO.setId(regionIntroduce.getId());
		regionIntroduceDO.setTitle(regionIntroduce.getTitle());
		regionIntroduceDO.setType(regionIntroduce.getType());*/
		if(0 == regionIntroduce.getId()){
			return RegionIntroduceClientServiceRef.add(regionIntroduce);
		}else{
			return RegionIntroduceClientServiceRef.update(regionIntroduce);
		}
	}

	@Override
	public List<RegionIntroduceDO> queryRegionIntroduceList(RegionIntroduceQuery regionIntroduceQuery) {
		return RegionIntroduceClientServiceRef.queryRegionIntroduceList(regionIntroduceQuery);
	}

	@Override
	public boolean delete(long id) {
		return false;
	}

	@Override
	public RegionIntroduceDO getRegionIntroduceDO(long id) {
		return null;
	}

}
