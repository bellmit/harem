package com.yimayhd.harem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.service.RcBoothService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;

public class RcBoothServiceImpl implements RcBoothService {
	
	@Autowired
	private BoothClientServer boothClientService;

	@Override
	public RCPageResult<BoothDO> getBoothResult(BoothQuery boothQuery) {
		return boothClientService.getBoothResult(boothQuery);
	}

	@Override
	public RcResult<BoothDO> getBoothById(long id) {
		return boothClientService.getBoothById(id);
	}

	@Override
	public RcResult<Boolean> save(BoothDO boothDO) {
		return boothClientService.insert(boothDO);
	}

	@Override
	public RcResult<Boolean> update(BoothDO boothDO) {
		return boothClientService.update(boothDO);
	}

}
