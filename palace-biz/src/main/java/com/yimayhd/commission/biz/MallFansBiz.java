package com.yimayhd.commission.biz;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.repo.MallFansRepo;
import com.yimayhd.marketing.client.model.domain.MallFansDO;
import com.yimayhd.marketing.client.model.param.mallfans.MallFansUpdateDTO;
import com.yimayhd.marketing.client.model.result.SpmResult;

public class MallFansBiz {

	private static final Logger logger = LoggerFactory.getLogger(MallFansBiz.class);
	
	@Autowired
	private MallFansRepo mallFansRepo;
	
	public SpmResult<MallFansDO> updateLevel(MallFansUpdateDTO updateDTO) {
		
		SpmResult<MallFansDO> baseResult = null;
		try{
			if (	
					null == updateDTO 
					|| updateDTO.getDomainId() <= 0
					|| updateDTO.getLevelId() <= 0
					|| updateDTO.getSettingType() <= 0
			) {
				logger.error("MallFansBiz.updateLevel end because of param error,param:" + JSON.toJSONString(updateDTO));
				return baseResult;
			}
			
			baseResult = mallFansRepo.updateLevel(updateDTO);
			if(baseResult == null || !baseResult.isSuccess() || baseResult.getT() == null){
				logger.error("MallFansBiz.updateLevel unexpected result,param:{},result:{}", JSON.toJSONString(updateDTO), JSON.toJSONString(baseResult));
			}
		}catch(Exception e){
			logger.error("MallFansBiz.updateLevel exceptions occur,param:{},ex:{}", JSON.toJSONString(updateDTO), e);
		}
		return baseResult;
	}

}
