package com.yimayhd.harem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommScenicServiceImpl implements CommScenicService {
	private static final Logger log = LoggerFactory.getLogger(CommScenicServiceImpl.class);

	@Autowired
	private ResourcePublishService resourcePublishServiceRef;

	@Override
	public ICResult<Long> save(ItemDO itemDo) throws Exception {
		resourcePublishServiceRef.publishScenic(itemDo);
		return null;
	}



}
