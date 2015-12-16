package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.result.ICResult;
public interface CommScenicService {

	/**
	 * 保存景区商品
	 * @param itemDo
	 * @return
	 * @throws Exception
	 */
	public ICResult<Long> save(ItemDO itemDo) throws Exception;
	
	

}
