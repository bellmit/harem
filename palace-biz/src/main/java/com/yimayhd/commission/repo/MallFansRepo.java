package com.yimayhd.commission.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.marketing.client.model.domain.MallFansDO;
import com.yimayhd.marketing.client.model.param.mallfans.MallFansUpdateDTO;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.marketing.client.service.MallFansService;

public class MallFansRepo {

	private static final Logger logger = LoggerFactory.getLogger(MallFansRepo.class);
	
	@Autowired
	private MallFansService mallFansService;
	
	public SpmResult<MallFansDO> updateLevel(MallFansUpdateDTO updateDTO){
		SpmResult<MallFansDO> baseResult = null;
		try{
			baseResult = mallFansService.updateLevel(updateDTO);
		}catch(Exception e){
			logger.error("MallFansRepo.updateLevel exceptions occur,param:{},ex:{}", JSON.toJSONString(updateDTO), e);
		}
		return baseResult;
	}
	
}
