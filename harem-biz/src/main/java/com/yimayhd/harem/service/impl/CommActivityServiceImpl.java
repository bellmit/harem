package com.yimayhd.harem.service.impl;

import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.HotelPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.tradecenter.client.model.enums.ReduceType;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommActivityServiceImpl implements CommActivityService {
	private static final Logger log = LoggerFactory.getLogger(CommActivityServiceImpl.class);

	@Autowired
	private ItemPublishService itemPublishService;
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	@Override
	public ItemPubResult add(ItemVO itemVO) throws Exception {
		 CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
	        ItemDO itemDO = ItemVO.getItemDO(itemVO);
	        itemDO.setSubTitle("");
	        itemDO.setOneWord("");
	    	itemDO.setCredit(0);
			itemDO.setPoint(0);
			itemDO.setOriginalCredit(0);
			itemDO.setOriginalPoint(0);
			itemDO.setOriginalPrice(0);
			itemDO.setStockNum(9999);
	        commonItemPublishDTO.setItemDO(itemDO);
	        commonItemPublishDTO.setItemSkuDOList(itemDO.getItemSkuDOList());
	        ItemFeature itemFeature = new ItemFeature(null);
	        itemFeature.put(ItemFeatureKey.REDUCE_TYPE, ReduceType.BEFORE_PAY);
	        itemDO.setItemFeature(itemFeature);
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

	@Override
	public void update(ItemVO itemVO)throws Exception{
		 //修改的时候要先取出来，在更新
		 ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		 ItemResult itemResult = itemQueryServiceRef.getItem(itemVO.getId(), itemOptionDTO);
		 if(null == itemResult){
	            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
	            throw new BaseException("查询商品，返回结果错误");
	        }else if(!itemResult.isSuccess()){
	            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
	            throw new NoticeException(itemResult.getResultMsg());
	        }
		 ItemDO itemDB = itemResult.getItem();
		  if(null != itemDB) {
	            HotelPublishDTO hotelPublishDTO = new HotelPublishDTO();
	            hotelPublishDTO.setItemDO(itemDB);

	            //组装
	            ItemDO itemDO = ItemVO.getItemDO(itemVO);
	            //酒店提前预定时间
	            if (null != itemVO.getEndBookTimeLimit()) {
	                if (null != itemVO.getItemFeature()) {
	                    itemDB.getItemFeature().put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
	                } else {
	                    ItemFeature itemFeature = new ItemFeature(null);
	                    itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
	                    itemDB.setItemFeature(itemFeature);
	                }
	            }
	            //商品名称
	            itemDB.setTitle(itemDO.getTitle());
	            //商品说明
	            itemDB.setOneWord(itemDO.getOneWord());
	            //商品价格
	            itemDB.setPrice(itemDO.getPrice());
	            //商品图片
	            if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
					itemDB.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC,itemVO.getSmallListPic());
				}
				if(StringUtils.isNotBlank(itemVO.getBigListPic())){
					itemDB.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC,itemVO.getBigListPic());
				}
				if(StringUtils.isNotBlank(itemVO.getCoverPics())){
					itemDB.addPicUrls(ItemPicUrlsKey.COVER_PICS, itemVO.getCoverPics());

				}

	}
	}


	



}
