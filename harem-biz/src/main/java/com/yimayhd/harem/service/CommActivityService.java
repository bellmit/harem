package com.yimayhd.harem.service;

import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
public interface CommActivityService {

	/**
	 * 保存景区商品
	 * @param itemVO
	 * @return
	 * @throws Exception
	 */
	public ItemPubResult add(ItemVO itemVO) throws Exception;

	public void update(ItemVO itemVO)throws Exception;
	
	

}
