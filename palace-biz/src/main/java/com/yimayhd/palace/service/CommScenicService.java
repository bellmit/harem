package com.yimayhd.palace.service;

import com.yimayhd.palace.model.CommScenicVO;
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
