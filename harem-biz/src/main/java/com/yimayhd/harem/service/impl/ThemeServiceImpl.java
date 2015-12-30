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
		if(null != themeVoQuery ){
			TagInfoDTO tagInfoDTO = new TagInfoDTO();
			tagInfoDTO.setPageNo(themeVoQuery.getPageNumber());
			tagInfoDTO.setPageSize(themeVoQuery.getPageSize());
			if(0 != themeVoQuery.getType() ){
				TagType tt = TagType.getByType(themeVoQuery.getType());
				if(null != tt ){
					tagInfoDTO.setOutType(tt.getType());
				}
			}
			
			BasePageResult<ComTagDO> res = comCenterService.selectTagInfoPage(tagInfoDTO);
			if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())){//res.getValue()
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
		BaseResult<ComTagDO> res = comCenterService.selectByPrimaryKey(id);
		if(null != res && res.isSuccess() && null != res.getValue()){
			ThemeVo themeVo = new ThemeVo();
			themeVo.setId(id);
			themeVo.setName(res.getValue().getName());
			themeVo.setOutType(res.getValue().getOutType());
			themeVo.setScore(res.getValue().getScore());
			themeVo.setStatus(res.getValue().getStatus());
			return themeVo;
		}
		return null;
	}

	@Override
	public ThemeVo saveOrUpdate(ThemeVo themeVo) throws Exception {
		if(null == themeVo){
			throw new Exception("parameters [themeVo] cannot be empty");
		}
		TagInfoAddDTO tagInfoAddDTO = new TagInfoAddDTO();
		if(themeVo.getId() == 0 ){
			tagInfoAddDTO.setName(themeVo.getName());
			tagInfoAddDTO.setScore(themeVo.getScore());
			tagInfoAddDTO.setOutType(themeVo.getOutType());
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
				tagInfoAddDTO.setOutType(themeVo.getOutType());
				BaseResult<ComTagDO> updateRes = comCenterService.updateComTagInfo(tagInfoAddDTO); 
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
