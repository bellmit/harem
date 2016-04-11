package com.yimayhd.commission.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.marketing.client.model.domain.LevelDO;
import com.yimayhd.marketing.client.model.query.LevelQuery;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.marketing.client.service.LevelService;

public class LevelRepo {

	private static final Logger logger = LoggerFactory.getLogger(LevelRepo.class);
	
	@Autowired
	private LevelService levelService;
	
	public SpmResult<List<LevelDO>> getAll(LevelQuery query){
		SpmResult<List<LevelDO>> baseResult = null;
		try{
			baseResult = levelService.getAll(query);
		}catch(Exception e){
			logger.error("LevelRepo.getAll exceptions occur,ex:", e);
		}
		return baseResult;
	}
}
