package com.yimayhd.commission.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commission.repo.LevelRepo;
import com.yimayhd.marketing.client.model.domain.LevelDO;
import com.yimayhd.marketing.client.model.query.LevelQuery;
import com.yimayhd.marketing.client.model.result.SpmResult;

public class LevelBiz {

	private static final Logger logger = LoggerFactory.getLogger(LevelBiz.class);
	
	@Autowired
	private LevelRepo levelRepo;
	
	public SpmResult<List<LevelDO>> getAll(LevelQuery query){
		SpmResult<List<LevelDO>> baseResult = null;
		try{
			baseResult = levelRepo.getAll(query);
		}catch(Exception e){
			logger.error("LevelBiz.getAll exceptions occur,ex:", e);
		}
		return baseResult;
	}
}
