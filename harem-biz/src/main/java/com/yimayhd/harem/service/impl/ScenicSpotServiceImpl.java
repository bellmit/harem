package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.model.vo.ScenicSpotVO;
import com.yimayhd.harem.service.ScenicSpotService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicSpotServiceImpl implements ScenicSpotService {

	@Autowired
	private ItemQueryService itemQueryService;
	
	@Override
	public List<ScenicDO> getList(ScenicSpotVO scenicSpotVO) throws Exception {
		
		return null;
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
	public void setScenicStatusList(List<Integer> idList, int scenicStatus)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScenicStatus(long id, int scenicStatus) throws Exception {
	
	}
   
}
