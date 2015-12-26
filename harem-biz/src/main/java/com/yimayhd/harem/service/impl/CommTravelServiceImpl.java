package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.harem.service.CommTravelService;
import com.yimayhd.harem.service.TagRPCService;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class CommTravelServiceImpl implements CommTravelService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	protected ItemQueryService itemQueryServiceRef;
	@Resource
	protected ItemPublishService itemPublishServiceRef;
	@Resource
	protected TagRPCService tagService;

	@Override
	public <T extends BaseTravel> T getById(long id, Class<T> clazz) {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LogUtil.requestLog(log, "itemQueryServiceRef.getLineResult", id);
		LineResult lineResult = itemQueryServiceRef.getLineResult(id);
		LogUtil.resultLog(log, "itemQueryServiceRef.getLineResult", lineResult);
		List<ComTagDO> tags = tagService.findAllTag(id, TagType.LINETAG);
		T travel = null;
		try {
			travel = clazz.newInstance();
			travel.init(lineResult, tags);
		} catch (Exception e) {
			log.error("解析线路信息失败", e);
			throw new BaseException("解析线路信息失败");
		}
		return travel;
	}

	public long publishLine(BaseTravel travel) {
		LinePublishResult publishLine = null;
		if (travel.getBaseInfo().getId() > 0) {
			publishLine = this.updateLine(travel);
		} else {
			publishLine = this.saveLine(travel);
		}
		tagService.addTagRelation(publishLine.getLineId(), TagType.LINETAG, travel.getTagIdList(),
				publishLine.getCreateTime());
		return publishLine.getLineId();
	}

	/**
	 * 更新
	 * 
	 * @param travel
	 * @return
	 */
	private LinePublishResult updateLine(BaseTravel travel) {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTOForUpdate();
		LogUtil.requestLog(log, "itemPublishServiceRef.updatePublishLine");
		LinePublishResult publishLine = itemPublishServiceRef.updatePublishLine(linePublishDTO);
		LogUtil.resultLog(log, "itemPublishServiceRef.updatePublishLine", publishLine);
		return publishLine;
	}

	/**
	 * 保存
	 * 
	 * @param travel
	 * @return
	 */
	private LinePublishResult saveLine(BaseTravel travel) {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTOForSave();
		LogUtil.requestLog(log, "itemPublishServiceRef.publishLine");
		LinePublishResult publishLine = itemPublishServiceRef.publishLine(linePublishDTO);
		LogUtil.resultLog(log, "itemPublishServiceRef.publishLine", publishLine);
		return publishLine;
	}

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		LogUtil.requestLog(log, "itemQueryServiceRef.pageQueryLine", query);
		ICPageResult<LineDO> pageQueryLine = itemQueryServiceRef.pageQueryLine(query);
		LogUtil.resultLog(log, "itemQueryServiceRef.pageQueryLine", pageQueryLine);
		int totalCount = pageQueryLine.getTotalCount();
		List<LineDO> itemList = pageQueryLine.getList();
		if (itemList == null) {
			itemList = new ArrayList<LineDO>();
		}
		return new PageVO<LineDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}
}
