package com.yimayhd.harem.service;

import com.yimayhd.harem.model.CommScenicVO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
public interface CommScenicService {

	/**
	 * 保存景区商品
	 * @param itemDo
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult save(CommScenicVO commScenic) throws Exception;
	
	

}
