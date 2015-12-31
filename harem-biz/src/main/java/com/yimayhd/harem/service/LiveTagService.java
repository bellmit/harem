package com.yimayhd.harem.service;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.LiveTopic;

/**
 * 直播标签
 * 
 * @author yebin
 *
 */
public interface LiveTagService {
	/**
	 * 分页查询直播标签
	 * 
	 * @param tagInfoDTO
	 * @return
	 */
	PageVO<ComTagDO> pageQueryLiveTag(TagInfoDTO tagInfoDTO);

	/**
	 * 新增直播标签
	 * 
	 * @param tag
	 */
	void addLiveTag(LiveTopic tag);
}
