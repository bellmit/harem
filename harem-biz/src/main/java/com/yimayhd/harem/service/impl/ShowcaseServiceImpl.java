package com.yimayhd.harem.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.service.ShowcaseService;
import com.yimayhd.resourcecenter.model.enums.RelatedType;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class ShowcaseServiceImpl implements ShowcaseService {

	@Autowired ShowcaseClientServer showcaseClientServerRef;
	
	public List<ShowCaseResult> getListShowCaseResult(int type) {
		ShowcaseQuery  showcaseQuery = new ShowcaseQuery ();
		RelatedType relatedType = RelatedType.getByType(type);
		if(null == relatedType ){
			return null;
		}
		showcaseQuery.setTitle(relatedType.getDesc());
		RCPageResult<ShowCaseResult>  res = showcaseClientServerRef.getShowcaseResult(showcaseQuery);
		if(null != res && CollectionUtils.isNotEmpty(res.getList())){
			return res.getList();
		}
		return null;
	}

}
