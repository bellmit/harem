package com.yimayhd.harem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.LiveTopic;
import com.yimayhd.harem.repo.TagRepo;
import com.yimayhd.harem.service.LiveTagService;

public class LiveTagServiceImpl implements LiveTagService {
	@Autowired
	private TagRepo tagRepo;

	@Override
	public PageVO<ComTagDO> pageQueryLiveTag(TagInfoDTO tagInfoDTO) {
		return tagRepo.pageQueryTag(tagInfoDTO);
	}

	@Override
	public void addLiveTag(LiveTopic tag) {
		// TODO Auto-generated method stub

	}

}
