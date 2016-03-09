package com.yimayhd.gf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.gf.service.BbsService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.snscenter.client.domain.SnsMasterDO;
import com.yimayhd.snscenter.client.domain.SnsModuleDO;
import com.yimayhd.snscenter.client.query.SnsMasterPageQuery;
import com.yimayhd.snscenter.client.query.SnsModulePageQuery;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsMasterService;
import com.yimayhd.snscenter.client.service.SnsModuleService;
import com.yimayhd.snscenter.client.service.SnsPostsService;

public class BbsServiceImpl implements BbsService{
	
	@Autowired
	private SnsModuleService snsModuleService;
	@Autowired
	private SnsPostsService snsPostsService;
	@Autowired
	private SnsMasterService snsMasterService;
	@Override
	public PageVO<SnsModuleDO> moduleQueryList(SnsModulePageQuery snsModulePageQuery) {
		
		BasePageResult<SnsModuleDO> pageResult = snsModuleService.pageQuery(snsModulePageQuery);
		
		if(null != pageResult && pageResult.isSuccess()){
			PageVO<SnsModuleDO> pageVO = new PageVO<SnsModuleDO>(snsModulePageQuery.getPageNo(), snsModulePageQuery.getPageSize(), pageResult.getTotalCount(), pageResult.getList());
			return pageVO;
		}
		
		return null;
	}
	@Override
	public BaseResult<SnsModuleDO> updateSnsModule(SnsModuleDO bbsModuleDO) {
		BaseResult<SnsModuleDO> updateSnsModuleResult = snsModuleService.updateSnsModule(bbsModuleDO);
		return updateSnsModuleResult;
	}
	@Override
	public BaseResult<SnsModuleDO> saveSnsModule(SnsModuleDO bbsModuleDO) {
		BaseResult<SnsModuleDO> saveBbsModuleResult = snsModuleService.saveSnsModule(bbsModuleDO);
		return saveBbsModuleResult;
	}
	@Override
	public BaseResult<SnsModuleDO> selectSnsModuleById(long id) {
		BaseResult<SnsModuleDO> selectResult = snsModuleService.selectSnsModuleById(id);
		return selectResult;
	}
	@Override
	public BaseResult<SnsMasterDO> saveBbsMaster(SnsMasterDO bbsMasterDO) {
		BaseResult<SnsMasterDO> baseResult = snsMasterService.saveSnsMaster(bbsMasterDO);
		return baseResult;
	}
	@Override
	public BaseResult<SnsMasterDO> updateBbsMaster(SnsMasterDO bbsMasterDO) {
		BaseResult<SnsMasterDO> updateSnsMasterResult = snsMasterService.updateSnsMaster(bbsMasterDO);
		return updateSnsMasterResult;
	}
	@Override
	public BaseResult<SnsMasterDO> getBbsMasterById(long parseLong) {
		BaseResult<SnsMasterDO> snsMasterResult = snsMasterService.getSnsMasterById(parseLong);
		return snsMasterResult;
	}
	@Override
	public PageVO<SnsMasterDO> masterQueryList(SnsMasterPageQuery bbsMasterPageQuery) {
		BasePageResult<SnsMasterDO> pageResult = snsMasterService.pageQuery(bbsMasterPageQuery);
		
		if(null != pageResult && pageResult.isSuccess()){
			PageVO<SnsMasterDO> pageVO = new PageVO<SnsMasterDO>(bbsMasterPageQuery.getPageNo(), bbsMasterPageQuery.getPageSize(), pageResult.getTotalCount(), pageResult.getList());
			return pageVO;
		}
		
		return null;
	}

}
