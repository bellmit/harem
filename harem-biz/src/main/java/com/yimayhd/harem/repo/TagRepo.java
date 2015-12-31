package com.yimayhd.harem.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.util.RepoUtils;

/**
 * 标签Repo
 * 
 * @author yebin
 *
 */
public class TagRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ComCenterService comCenterServiceRef;

	public void addTagRelation(long outId, TagType tagType, List<Long> tagIdList, Date date) {
		TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
		tagRelationInfoDTO.setTagType(tagType.getType());
		tagRelationInfoDTO.setOutId(outId);
		tagRelationInfoDTO.setOrderTime(date);
		tagRelationInfoDTO.setList(tagIdList);
		RepoUtils.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
		BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
	}

	public List<ComTagDO> findAllTag(long outId, TagType tagType) {
		RepoUtils.requestLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", outId, tagType.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.getTagInfoByOutIdAndType(outId, tagType.name());
		RepoUtils.resultLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", tagResult);
		return tagResult.getValue();
	}

	public PageVO<ComTagDO> pageQueryTag(TagInfoDTO tagInfoDTO) {
		RepoUtils.requestLog(log, "comCenterServiceRef.selectTagInfoPage", tagInfoDTO);
		BasePageResult<ComTagDO> result = comCenterServiceRef.selectTagInfoPage(tagInfoDTO);
		RepoUtils.resultLog(log, "comCenterServiceRef.selectTagInfoPage", result);
		int totalCount = result.getTotalCount();
		List<ComTagDO> itemList = result.getList();
		if (CollectionUtils.isEmpty(itemList)) {
			itemList = new ArrayList<ComTagDO>();
		}
		return new PageVO<ComTagDO>(tagInfoDTO.getPageNo(), tagInfoDTO.getPageSize(), totalCount, itemList);
	}

}
