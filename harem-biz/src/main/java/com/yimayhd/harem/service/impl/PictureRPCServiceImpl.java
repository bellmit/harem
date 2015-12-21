package com.yimayhd.harem.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.service.PictureRPCService;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class PictureRPCServiceImpl implements PictureRPCService {
	private static Logger log = LoggerFactory.getLogger(UserRPCServiceImpl.class);
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	@Override
	public PageVO<PicturesDO> pageQueryPictureList(PicturesPageQuery query) {
		ICPageResult<PicturesDO> queryPicturesResult = itemQueryServiceRef.queryPictures(query);
		int totalCount = 0;
		List<PicturesDO> itemList = new ArrayList<PicturesDO>();
		if (queryPicturesResult != null && queryPicturesResult.isSuccess()) {
			totalCount = queryPicturesResult.getTotalCount();
			if (CollectionUtils.isNotEmpty(queryPicturesResult.getList())) {
				itemList.addAll(queryPicturesResult.getList());
			}
		} else {
			log.error(MessageFormat.format("检索图片列表失败：query={0}", JSON.toJSONString(query)));
			log.error(MessageFormat.format("检索图片列表失败：result={0}", JSON.toJSONString(queryPicturesResult)));
			throw new BaseException("检索图片列表失败");
		}
		return new PageVO<PicturesDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public List<PicturesDO> queryTopPictureList(PictureOutType outType, long outId, int limit) {
		PicturesPageQuery ppq = new PicturesPageQuery();
		ppq.setOutId(outId);
		ppq.setIsTop(true);
		ppq.setOutType(outType.getValue());
		ppq.setPageNo(1);
		ppq.setPageSize(limit);
		return pageQueryPictureList(ppq).getItemList();
	}

}
