package com.yimayhd.harem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.harem.repo.LineRepo;
import com.yimayhd.harem.service.CommTravelService;
import com.yimayhd.harem.service.TagRepo;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import org.springframework.beans.factory.annotation.Autowired;

public class CommTravelServiceImpl implements CommTravelService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected LineRepo lineRepo;
	@Autowired
	protected TagRepo tagRepo;

	@Override
	public <T extends BaseTravel> T getById(long id, Class<T> clazz) {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = lineRepo.getLineById(id);
		List<ComTagDO> tags = tagRepo.findAllTag(id, TagType.LINETAG);
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

	@Override
	public long publishLine(BaseTravel travel) {
		LinePublishResult publishLine = null;
		long lineId = travel.getBaseInfo().getId();
		if (lineId > 0) {
			publishLine = lineRepo.updateLine(travel.toLinePublishDTOForUpdate());
		} else {
			publishLine = lineRepo.saveLine(travel.toLinePublishDTOForSave());
		}
		if (publishLine.getLineId() > 0) {
			tagRepo.addTagRelation(publishLine.getLineId(), TagType.LINETAG, travel.getTagIdList(),
					publishLine.getCreateTime());
		}
		return publishLine.getLineId();
	}

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		return lineRepo.pageQueryLine(query);
	}
}
