package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.ThemeVo;
import com.yimayhd.harem.model.query.ThemeVoQuery;
import com.yimayhd.harem.service.ThemeService;

public class ThemeServiceImpl implements ThemeService {
	
	private static final Logger log = LoggerFactory.getLogger(ThemeServiceImpl.class);
	
	@Autowired ComCenterService comCenterService;

	@Override
	public PageVO<ComTagDO> getPageTheme(ThemeVoQuery themeVoQuery) throws Exception {
		int totalCount = 0;
		List<ComTagDO> list = new ArrayList<ComTagDO>();
		if(null != themeVoQuery && null != TagType.getByType(themeVoQuery.getType()) ){
			TagInfoDTO tagInfoDTO = new TagInfoDTO();
			tagInfoDTO.setPageNo(themeVoQuery.getPageNumber());
			tagInfoDTO.setPageSize(themeVoQuery.getPageSize());
			tagInfoDTO.setOutType(themeVoQuery.getType());
			BasePageResult<ComTagDO> res = comCenterService.selectTagInfoPage(tagInfoDTO);
			if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(null)){//res.getValue()
				totalCount=res.getTotalCount();
				list = res.getList();
			}
		}else{
			log.error("Request {} error: query={}", "comCenterService.selectTagListByTagType",JSON.toJSONString(themeVoQuery));
		}
		return  new PageVO<ComTagDO>(themeVoQuery.getPageNumber(), themeVoQuery.getPageSize(), totalCount, list);
	}

	@Override
	public List<ComTagDO> getListTheme(ThemeVoQuery themeVoQuery) throws Exception {
		
		return null;
	}

	
	@Override
	public ThemeVo getById(long id) throws Exception {
		Random  s = new Random();
		int type=s.nextInt(2);
		ThemeVo t = new ThemeVo();
		t.setGmtCreated(new Date());
		t.setId(type);
		t.setGmtModified(new Date());
		t.setScore(type);
		t.setStatus(1);
		if(1==type){
			t.setName("活动主题"+type);	
		}else{
			t.setName("俱乐部主题"+type);
		}
		t.setOutType(type+1);
		return t;
	}

	@Override
	public ThemeVo saveOrUpdate(ThemeVo themeVo) throws Exception {
		if(null == themeVo){
			throw new Exception("parameters [themeVo] cannot be empty");
		}
		TagInfoAddDTO tagInfoAddDTO = null;
		if(themeVo.getId() == 0 ){
			tagInfoAddDTO = new TagInfoAddDTO();
			tagInfoAddDTO.setName(themeVo.getName());
			tagInfoAddDTO.setScore(themeVo.getScore());
			tagInfoAddDTO.setOutType(themeVo.getType());
			BaseResult<ComTagDO> insertRes = comCenterService.addComTagInfo(tagInfoAddDTO);
			if(null != insertRes && insertRes.isSuccess() ){
				themeVo.setId(insertRes.getValue().getId());
				return themeVo;
			}
		}else{
			 BaseResult<ComTagDO>  dbRes =comCenterService.selectByPrimaryKey(themeVo.getId());
			 if(null != dbRes && dbRes.isSuccess() && null != dbRes.getValue()){
				tagInfoAddDTO.setTagId(dbRes.getValue().getId());
				tagInfoAddDTO.setName(themeVo.getName());
				tagInfoAddDTO.setScore(themeVo.getScore());
				tagInfoAddDTO.setOutType(themeVo.getType());
				//BaseResult<ComTagDO> updateRes = comCenterService.updateComTagInfo(tagInfoAddDTO); 
				BaseResult<ComTagDO> updateRes = null;
				if(null != updateRes && updateRes.isSuccess()){
					themeVo.setId(updateRes.getValue().getId());
					return themeVo;
				}
			 }
		}
		return null;
	}

	@Override
	public void modify(ThemeVo themeVo) throws Exception {
	}

	@Override
	public void delete(long id) throws Exception {
	}

}
