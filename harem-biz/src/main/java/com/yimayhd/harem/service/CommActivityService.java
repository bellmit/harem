package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
public interface CommActivityService {

	/**
	 * 保存景区商品
	 * @param itemDo
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult save(CommonItemPublishDTO commonItemPublishDTO) throws Exception;
	
	

}
