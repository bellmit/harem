package com.yimayhd.harem.service;

import java.util.Date;
import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;

public interface TagRPCService {

	/**
	 * 增加Tag关系
	 * 
	 * @param outId
	 * @param tagType
	 * @param tagIdList
	 * @param date
	 */
	void addTagRelation(long outId, TagType tagType, List<Long> tagIdList, Date date);

	/**
	 * 查询分类下的Tag
	 * 
	 * @param outId
	 * @param tagType
	 * @return
	 */
	List<ComTagDO> findAllTag(long outId, TagType tagType);
}
