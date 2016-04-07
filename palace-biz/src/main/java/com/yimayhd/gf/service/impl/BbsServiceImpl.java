package com.yimayhd.gf.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.gf.model.BbsPostsQueryVO;
import com.yimayhd.gf.service.BbsService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.snscenter.client.domain.SnsMasterDO;
import com.yimayhd.snscenter.client.domain.SnsModuleDO;
import com.yimayhd.snscenter.client.domain.SnsPostsDO;
import com.yimayhd.snscenter.client.dto.PostsResultDTO;
import com.yimayhd.snscenter.client.query.SnsMasterPageQuery;
import com.yimayhd.snscenter.client.query.SnsModulePageQuery;
import com.yimayhd.snscenter.client.query.SnsPostsQuery;
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
	
	public PageVO<PostsResultDTO> postsQueryList(SnsPostsQuery postsQuery){
		BasePageResult<PostsResultDTO> basePageResult = snsPostsService.pageQuery(postsQuery);
		if(null != basePageResult && basePageResult.isSuccess()){
			PageVO<PostsResultDTO> pageVO = new PageVO<PostsResultDTO>(postsQuery.getPageNo(), postsQuery.getPageSize(), basePageResult.getTotalCount(), basePageResult.getList());
			return pageVO;
		}
		
		return null;
		
	}
	
	public BaseResult<SnsPostsDO> getPostsDetail(long id){
		BaseResult<SnsPostsDO> snsPostsResult = snsPostsService.getSnsPosts(id);
		return snsPostsResult;
	}

	
	public BaseResult<SnsPostsDO> savePosts(SnsPostsDO bbsPostsDO){
		BaseResult<SnsPostsDO> saveSnsPostsResult = snsPostsService.saveSnsPosts(bbsPostsDO);
		return saveSnsPostsResult;
	}
	
	public BaseResult<SnsPostsDO> updatePosts(SnsPostsDO snsPostsDO){
		BaseResult<SnsPostsDO> updateSnsPostsResult = snsPostsService.updateSnsPosts(snsPostsDO);
		return updateSnsPostsResult;
	}
	@Override
	public BaseResult<Boolean> updatePostsStatus(SnsPostsDO bbsPostsDO) {
		BaseResult<Boolean> updateStatusResult = snsPostsService.updateSnsPostsStatus(bbsPostsDO);
		return updateStatusResult;
	}
	@Override
	public PageVO<PostsResultDTO> postsQueryList(BbsPostsQueryVO postsQuery) {
		
		
		SnsPostsQuery snsPostsQuery = setSnsPostsQuery(postsQuery);
		
		
		BasePageResult<PostsResultDTO> basePageResult = snsPostsService.pageQuery(snsPostsQuery );
		if(null != basePageResult && basePageResult.isSuccess()){
			PageVO<PostsResultDTO> pageVO = new PageVO<PostsResultDTO>(snsPostsQuery.getPageNo(), snsPostsQuery.getPageSize(), basePageResult.getTotalCount(), basePageResult.getList());
			return pageVO;
		}
		
		return null;
	}
	
	private SnsPostsQuery setSnsPostsQuery(BbsPostsQueryVO postsQuery) {
		
		SnsPostsQuery snsPostsQuery = new SnsPostsQuery();
		snsPostsQuery.setStartTime(getDateStart(postsQuery.getStartTime()));
		snsPostsQuery.setEndTime(getDateEnd(postsQuery.getEndTime()));
		if(null != postsQuery.getModuleId()){
			snsPostsQuery.setModuleIds(postsQuery.getModuleId());
		}
		if(null != postsQuery.getStatus()){
			snsPostsQuery.setStatuses(postsQuery.getStatus());
		}
		snsPostsQuery.setTitle(postsQuery.getTitle());
		snsPostsQuery.setPageNo(postsQuery.getPageNumber());
		snsPostsQuery.setPageSize(postsQuery.getPageSize());
		return snsPostsQuery;
	}
	
	
	public static Date getDateEnd(String time){

    	if(StringUtils.isBlank(time)){
    		return null;
    	}
    	
		time  = time + " 23:59:59";
		
		Date end = parseDate(time, "yyyy-MM-dd HH:mm:ss");
		return end;
		
	}
	public static Date getDateStart(String time){
    	if(StringUtils.isBlank(time)){
    		return null;
    	}
		time  = time + " 00:00:00";
		
		Date end = parseDate(time, "yyyy-MM-dd HH:mm:ss");
		return end ;
		
	}
	
	public static Date parseDate(String dayFormat, String format){
		if( dayFormat == null || format == null || "".equals(format)){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(dayFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
