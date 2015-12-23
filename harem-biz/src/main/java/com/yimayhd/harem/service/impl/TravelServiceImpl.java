package com.yimayhd.harem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public abstract class TravelServiceImpl<T extends BaseTravel> {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	protected ItemQueryService itemQueryServiceRef;
	@Resource
	protected ItemPublishService itemPublishServiceRef;
	@Resource
	protected ComCenterService comCenterServiceRef;

	public T getById(long id, Class<T> clazz) throws Exception {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = null;
		LogUtil.requestLog(log, "itemQueryServiceRef.getLineResult", id);
		lineResult = itemQueryServiceRef.getLineResult(id);
		LogUtil.icResultLog(log, "itemQueryServiceRef.getLineResult", lineResult);
		LogUtil.requestLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", id, TagType.LINETAG.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.getTagInfoByOutIdAndType(id, TagType.LINETAG.name());
		LogUtil.ccResultLog(log, "comCenterServiceRef.getTagInfoByOutIdAndType", tagResult);
		List<ComTagDO> tags = tagResult.getValue();
		T travel = null;
		try {
			travel = createTravelInstance(lineResult, tags);
		} catch (Exception e) {
			LogUtil.exceptionLog(log, "解析线路信息失败", true, e, "lineResult", lineResult, "tags", tags);
		}
		return travel;
	}

	protected abstract T createTravelInstance(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception;

	protected long publishLine(BaseTravel travel) throws BaseException {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTO();
		LogUtil.requestLog(log, "itemPublishServiceRef.publishLine", linePublishDTO);
		LinePublishResult publishLine = itemPublishServiceRef.publishLine(linePublishDTO);
		LogUtil.icResultLog(log, "itemPublishServiceRef.publishLine", publishLine);
		TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
		tagRelationInfoDTO.setTagType(TagType.LINETAG.getType());
		tagRelationInfoDTO.setOutId(publishLine.getLineId());
		tagRelationInfoDTO.setOrderTime(publishLine.getCreateTime());
		tagRelationInfoDTO.setList(travel.getTagIdList());
		LogUtil.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
		BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
		LogUtil.ccResultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
		return publishLine.getLineId();
	}
}
