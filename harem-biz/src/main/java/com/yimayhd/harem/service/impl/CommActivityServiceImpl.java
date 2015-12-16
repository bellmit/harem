package com.yimayhd.harem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommActivityServiceImpl implements CommActivityService {
	private static final Logger log = LoggerFactory.getLogger(CommActivityServiceImpl.class);

	@Autowired
	private ItemPublishService itemPublishService;

	@Override
	public ItemPubResult save(CommonItemPublishDTO commonItemPublishDTO) throws Exception {
		itemPublishService.publishCommonItem(commonItemPublishDTO);
		return null;
	}


	



}
