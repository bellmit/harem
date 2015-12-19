package com.yimayhd.harem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommScenicServiceImpl implements CommScenicService {
	private static final Logger log = LoggerFactory.getLogger(CommScenicServiceImpl.class);

	@Autowired
	private ItemPublishService itemPublishService;


	@Override
	public ItemPubResult save(ScenicPublishDTO scenicPublishDTO) throws Exception {
		
		   ItemDO itemDO = scenicPublishDTO.getItemDO();
	        ItemFeature itemFeature = new ItemFeature(null);
	        //减库存方式
	        itemFeature.put(ItemFeatureKey.REDUCE_TYPE,1);
	        //未付款超时时间
	        itemFeature.put(ItemFeatureKey.NOT_PAY_TIMEOUT,3 * 24 * 3600 * 1000L);
	        //商品星级
	        itemFeature.put(ItemFeatureKey.GRADE,5);
	        //可预订时间，秒
	        itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT,5);
	        //需要提前多久预订，秒
	        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,5);
	        itemDO.setItemFeature(itemFeature);
	        itemDO.setOutType(2);
	        scenicPublishDTO.getScenicDO().setId(62);
	        itemDO.setRootCategoryId(34);//根类目
	        itemDO.setCategoryId(35);//景区分类id
	        itemDO.setSellerId(10000000);//发布人
	        itemDO.setItemType(2);
	        itemDO.setSource(1);
	        itemDO.setCredit(0);
	        itemDO.setPoint(0);
	        itemDO.setOriginalCredit(0);
	        itemDO.setOriginalPoint(0);
	        itemDO.setOriginalPrice(0);
	        itemDO.setPayType(0);
	        itemDO.setStatus(1);
	        
		
		ItemPubResult publicScenic = itemPublishService.publicScenic(scenicPublishDTO);
		return publicScenic;
	}



}
