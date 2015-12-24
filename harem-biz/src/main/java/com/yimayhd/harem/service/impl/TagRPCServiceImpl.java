package com.yimayhd.harem.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.service.TagRPCService;
import com.yimayhd.harem.util.LogUtil;

public class TagRPCServiceImpl implements TagRPCService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ComCenterService comCenterServiceRef;

	@Override
	public void addTagRelation(long outId, TagType tagType, List<Long> tagIdList, Date date) {
		TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
		tagRelationInfoDTO.setTagType(tagType.getType());
		tagRelationInfoDTO.setOutId(outId);
		tagRelationInfoDTO.setOrderTime(date);
		tagRelationInfoDTO.setList(tagIdList);
		LogUtil.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
		BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
		LogUtil.resultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
	}

	@Override
	public List<ComTagDO> findAllTag(long outId, TagType tagType) {
		LogUtil.requestLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", outId, tagType.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.getTagInfoByOutIdAndType(outId, tagType.name());
		LogUtil.resultLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", tagResult);
		return tagResult.getValue();
	}

}
