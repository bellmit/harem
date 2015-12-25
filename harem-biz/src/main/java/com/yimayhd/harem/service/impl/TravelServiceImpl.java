package com.yimayhd.harem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.service.TagRPCService;
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
	protected TagRPCService tagService;

	public T getById(long id, Class<T> clazz) throws Exception {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = null;
		LogUtil.requestLog(log, "itemQueryServiceRef.getLineResult", id);
		lineResult = itemQueryServiceRef.getLineResult(id);
		LogUtil.resultLog(log, "itemQueryServiceRef.getLineResult", lineResult);
		List<ComTagDO> tags = tagService.findAllTag(id, TagType.LINETAG);
		T travel = null;
		try {
			travel = createTravelInstance(lineResult, tags);
		} catch (Exception e) {
			log.error("解析线路信息失败", e);
			throw new BaseException("解析线路信息失败");
		}
		return travel;
	}

	protected abstract T createTravelInstance(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception;

	protected long save(BaseTravel travel) throws BaseException {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTO();
		LogUtil.requestLog(log, "itemPublishServiceRef.publishLine", linePublishDTO);
		LinePublishResult publishLine = itemPublishServiceRef.publishLine(linePublishDTO);
		LogUtil.resultLog(log, "itemPublishServiceRef.publishLine", publishLine);
		tagService.addTagRelation(publishLine.getLineId(), TagType.LINETAG, travel.getTagIdList(),
				publishLine.getCreateTime());
		return publishLine.getLineId();
	}

	protected long update(BaseTravel travel) throws BaseException {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTO();
		LogUtil.requestLog(log, "itemPublishServiceRef.publishLine", linePublishDTO);
		LinePublishResult publishLine = itemPublishServiceRef.updatePublishLine(linePublishDTO);
		LogUtil.resultLog(log, "itemPublishServiceRef.publishLine", publishLine);
		tagService.addTagRelation(publishLine.getLineId(), TagType.LINETAG, travel.getTagIdList(),
				publishLine.getCreateTime());
		return publishLine.getLineId();
	}

	protected long publishLine(BaseTravel travel) throws Exception {
		if (travel.getBaseInfo().getId() > 0) {
			return update(travel);
		} else {
			return save(travel);
		}
	}
}
