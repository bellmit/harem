package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicServiceImpl implements ScenicService {

	@Autowired
	private ItemQueryService itemQueryService;

	@Override
	public PageVO<ScenicDO> getList(ScenicPageQuery query) throws Exception {
		int totalCount = 0;
		List<ScenicDO> itemList = new ArrayList<ScenicDO>();
		ICPageResult<ScenicDO> pageResult = itemQueryService.pageQueryScenic(query);
		if (pageResult != null && pageResult.isSuccess()) {
			totalCount = pageResult.getTotalCount();
			if (CollectionUtils.isNotEmpty(pageResult.getList())) {
				itemList.addAll(pageResult.getList());
			}
		}
		return new PageVO<ScenicDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}
	
	
	

	@Override
	public ScenicDO getById(long id) throws Exception {
		
		return null;
	}

	@Override
	public ScenicDO add(ScenicDO scenicDO) throws Exception {
		return null;
	}

	@Override
	public void modify(ScenicDO scenicDO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScenicStatusList(List<Integer> idList, int scenicStatus) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScenicStatus(long id, int scenicStatus) throws Exception {

	}

}
