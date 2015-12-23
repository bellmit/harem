package com.yimayhd.harem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommActivityServiceImpl implements CommActivityService {
	private static final Logger log = LoggerFactory.getLogger(CommActivityServiceImpl.class);

	@Autowired
	private ItemPublishService itemPublishService;

	@Override
	public ItemPubResult add(ItemVO itemVO) throws Exception {
		 CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
	        ItemDO itemDO = ItemVO.getItemDO(itemVO);
	        itemDO.setSubTitle("");
	        itemDO.setOneWord("");
	        itemDO.setPicUrls("");
	    	itemDO.setCredit(0);
			itemDO.setPoint(0);
			itemDO.setOriginalCredit(0);
			itemDO.setOriginalPoint(0);
			itemDO.setOriginalPrice(0);
	        commonItemPublishDTO.setItemDO(itemDO);
	        commonItemPublishDTO.setItemSkuDOList(itemDO.getItemSkuDOList());
		
	        ItemPubResult itemPubResult =itemPublishService.publishCommonItem(commonItemPublishDTO);
		 if(null == itemPubResult){
	            log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
	            throw new BaseException("返回结果错误,新增失败 ");
	        } else if(!itemPubResult.isSuccess()){
	            log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
	            throw new BaseException(itemPubResult.getResultMsg());
	        }
		 return  itemPubResult;
	}


	



}
