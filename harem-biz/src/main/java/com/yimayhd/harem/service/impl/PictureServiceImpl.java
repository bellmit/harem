package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.repo.PictureRepo;
import com.yimayhd.harem.service.PictureService;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;

public class PictureServiceImpl implements PictureService {
	@Autowired
	private PictureRepo pictureRepo;

	@Override
	public PageVO<PicturesDO> pageQueryPictures(PicturesPageQuery query) {
		return pictureRepo.pageQueryPictures(query);
	}

	@Override
	public List<PicturesDO> queryTopPictures(PictureOutType outType, long outId, int limit) {
		return pictureRepo.queryTopPictures(outType, outId, limit);
	}

	@Override
	public List<PicturesDO> queryAllPictures(PictureOutType outType, long outId) {
		return pictureRepo.queryAllPictures(outType, outId);
	}

}
