package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;

public interface PictureRPCService {
	/**
	 * 分页查询图片
	 * 
	 * @param query
	 * @return
	 */
	PageVO<PicturesDO> pageQueryPictureList(PicturesPageQuery query);

	/**
	 * 查询置顶图片
	 * 
	 * @param outType
	 * @param outId
	 * @param limit
	 * @return
	 */
	List<PicturesDO> queryTopPictureList(PictureOutType outType, long outId, int limit);
}
