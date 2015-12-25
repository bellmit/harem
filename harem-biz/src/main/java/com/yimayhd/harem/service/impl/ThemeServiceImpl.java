package com.yimayhd.harem.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.model.ThemeVo;
import com.yimayhd.harem.model.query.ThemeVoQuery;
import com.yimayhd.harem.service.ThemeService;

public class ThemeServiceImpl implements ThemeService {
	
	@Autowired ComCenterService comCenterService;

	@Override
	public List<ComTagDO> getList(ThemeVoQuery themeVoQuery) throws Exception {
		if(null != themeVoQuery && null != TagType.getByType(themeVoQuery.getType()) ){
			BaseResult<List<ComTagDO>> res = comCenterService.selectTagListByTagType(TagType.getByType(themeVoQuery.getType()).name());
			if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getValue())){
				return res.getValue();
			}
		}
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
	public ThemeVo add(ThemeVo themeVo) throws Exception {
		return null;
	}

	@Override
	public void modify(ThemeVo themeVo) throws Exception {
	}

	@Override
	public void delete(long id) throws Exception {
	}

}
