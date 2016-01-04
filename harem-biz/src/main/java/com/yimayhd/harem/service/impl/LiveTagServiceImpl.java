package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.LiveTagVO;
import com.yimayhd.harem.repo.TagRepo;
import com.yimayhd.harem.service.LiveTagService;

public class LiveTagServiceImpl implements LiveTagService {
	@Autowired
	private TagRepo tagRepo;

	@Override
	public PageVO<ComTagDO> pageQueryLiveTag(TagInfoDTO tagInfoDTO) {
		tagInfoDTO.setOutType(TagType.LIVESUPTAG.getType());
		return tagRepo.pageQueryTag(tagInfoDTO);
	}

	@Override
	public ComTagDO getLveTagById(long id) {
		return tagRepo.getTagById(id);
	}

	@Override
	public void disableLiveTagById(long id) {
		tagRepo.updateTagStateById(id, TagRepo.STATUS_DISABLE);
	}

	@Override
	public void enableLiveTagById(long id) {
		tagRepo.updateTagStateById(id, TagRepo.STATUS_ENABLE);
	}

	@Override
	public void disableLiveTagByIdList(List<Long> idList) {
		tagRepo.updateTagStateByIdList(idList, TagRepo.STATUS_DISABLE);
	}

	@Override
	public void enableLiveTagByIdList(List<Long> idList) {
		tagRepo.updateTagStateByIdList(idList, TagRepo.STATUS_ENABLE);
	}

	@Override
	public void save(LiveTagVO tag) {
		if (tag.getId() > 0) {
			tagRepo.updateTag(tag.toTagInfo());
		} else {
			tagRepo.saveTag(tag.toTagInfo());
		}
	}
}
