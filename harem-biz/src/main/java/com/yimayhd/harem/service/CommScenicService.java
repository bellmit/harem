package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
public interface CommScenicService {

	/**
	 * 保存景区商品
	 * @param itemDo
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult save(ScenicPublishDTO scenicPublishDTO,Long[] check) throws Exception;
	
	

}
