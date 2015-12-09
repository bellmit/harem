package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.ScenicSpotListQuery;
import com.yimayhd.harem.model.vo.ScenicSpotVO;
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
	public ICPageResult<ScenicDO> getList(ScenicPageQuery scenicPageQuery) throws Exception {

		return itemQueryService.pageQueryScenic(scenicPageQuery);
	}

	@Override
	public ScenicDO getById(long id) throws Exception {
		ScenicDO scenicDO = new ScenicDO();
		scenicDO.setId(id);
		scenicDO.setName("景点" + id);
		scenicDO.setStatus(1);
		return scenicDO;
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
