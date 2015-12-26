package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public abstract class BaseTravel {
	private static final int LINE_ADULT_VID = 1;
	private static final int LINE_SINGLE_ROOM_VID = 4;
	protected long categoryId;
	protected long options;
	protected BaseInfo baseInfo;// 基础信息
	protected PriceInfo priceInfo;// 价格信息
	protected boolean readonly = false;

	public void init(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		// TODO YEBIN DO对象解析
		LineDO line = lineResult.getLineDO();
		if (line != null) {
			this.baseInfo = new BaseInfo(line, comTagDOs);
		}

		/*
		 * RouteDO route = lineResult.getRouteDO(); if (route != null &&
		 * this.baseInfo != null) {
		 * this.baseInfo.setTripImage(route.getPicture()); }
		 */
		parseTripInfo(lineResult);
		ItemDO itemDO = lineResult.getItemDO();
		if (itemDO != null) {
			this.categoryId = itemDO.getCategoryId();
			this.options = itemDO.getOptions();
			this.readonly = itemDO.getStatus() == ItemStatus.valid.getValue();
			this.priceInfo = new PriceInfo(lineResult.getItemDO(), lineResult.getItemSkuDOList());
		}
	}

	protected abstract void parseTripInfo(LineResult lineResult);

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	/**
	 * 获得线路发布DTO
	 * 
	 * @return
	 */
	public LinePublishDTO toLinePublishDTOForSave() {
		LinePublishDTO dto = new LinePublishDTO();
		dto.setLineDO(baseInfo.toLineDO());
		setRouteInfo(dto);
		dto.setItemDO(this.getItemDO());
		List<ItemSkuDO> itemSkuDOList = priceInfo.toItemSkuDOList(this.categoryId, this.getSellerId());
		dto.setItemSkuDOList(itemSkuDOList);
		return dto;
	}

	/**
	 * 获得线路发布DTO
	 * 
	 * @return
	 */
	public LinePublishDTO toLinePublishDTOForUpdate() {
		LinePublishDTO dto = this.toLinePublishDTOForSave();
		List<ItemSkuDO> itemSkuDOList = dto.getItemSkuDOList();
		Map<Long, ItemSkuDO> skuMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuDOList) {
			if (itemSkuDO.getId() > 0) {
				skuMap.put(itemSkuDO.getId(), itemSkuDO);
			}
		}
		if (this.baseInfo.getId() > 0) {
			List<ItemSkuDO> addSkuList = new ArrayList<ItemSkuDO>();
			List<ItemSkuDO> updateSkuList = new ArrayList<ItemSkuDO>();
			List<ItemSkuDO> deleteSkuList = new ArrayList<ItemSkuDO>();
			if (CollectionUtils.isNotEmpty(itemSkuDOList)) {
				for (ItemSkuDO itemSkuDO : deleteSkuList) {
					if (itemSkuDO.getId() <= 0) {
						addSkuList.add(itemSkuDO);
					}
				}
				List<Long> deletedSKU = this.priceInfo.getDeletedSKU();
				List<Long> updatedSKU = this.priceInfo.getUpdatedSKU();
				// 决定删除就不更新了
				updatedSKU.removeAll(deletedSKU);
				if (CollectionUtils.isNotEmpty(deletedSKU)) {
					for (long skuId : deletedSKU) {
						if (skuMap.containsKey(skuId)) {
							ItemSkuDO itemSkuDO = skuMap.get(skuId);
							deleteSkuList.add(itemSkuDO);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(updatedSKU)) {
					for (long skuId : updatedSKU) {
						if (skuMap.containsKey(skuId)) {
							ItemSkuDO itemSkuDO = skuMap.get(skuId);
							updateSkuList.add(itemSkuDO);
						}
					}
				}
			}
			dto.setAddItemSkuList(addSkuList);
			dto.setUpdItemSkuList(updateSkuList);
			dto.setDelItemSkuList(deleteSkuList);
		}
		return dto;
	}

	/**
	 * 获得商品标签
	 * 
	 * @return
	 */
	public List<Long> getTagIdList() {
		return baseInfo != null ? baseInfo.getTags() : new ArrayList<Long>();
	}

	/**
	 * 获取ItemDO
	 * 
	 * @return
	 */
	public ItemDO getItemDO() {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(this.priceInfo.getItemId());
		itemDO.setSellerId(this.getSellerId());
		itemDO.setCategoryId(this.categoryId);
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, this.priceInfo.getLimitBySecond());
		itemFeature.put(ItemFeatureKey.AGREEMENT, this.priceInfo.getImportantInfosCode());
		itemFeature.put(ItemFeatureKey.LINE_ADULT_VID, LINE_ADULT_VID);
		itemFeature.put(ItemFeatureKey.LINE_SINGLE_ROOM_VID, LINE_SINGLE_ROOM_VID);
		itemDO.setItemFeature(itemFeature);
		itemDO.setItemType(getItemType());
		itemDO.setPayType(1);
		itemDO.setSource(1);
		itemDO.setVersion(1);
		itemDO.setOptions(this.options);
		itemDO.setCategoryId(this.categoryId);
		itemDO.setTitle(this.baseInfo.getName());
		itemDO.setStockNum(0);
		itemDO.setSubTitle("");
		itemDO.setOneWord("");
		itemDO.setDescription("");
		if (StringUtils.isNotBlank(this.baseInfo.getProductImage())) {
			itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, this.baseInfo.getProductImage());
		}
		if (StringUtils.isNotBlank(this.baseInfo.getTripImage())) {
			itemDO.addPicUrls(ItemPicUrlsKey.COVER_PICS, this.baseInfo.getTripImage());
		}
		itemDO.setDetailUrl("");
		return itemDO;
	}

	protected long getSellerId() {
		long sellerId = 0;
		if (this.baseInfo != null) {
			sellerId = this.baseInfo.getPublisherId();
		}
		return sellerId;
	}

	public abstract void setRouteInfo(LinePublishDTO dto);

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getOptions() {
		return options;
	}

	public void setOptions(long options) {
		this.options = options;
	}

	protected abstract int getItemType();

	public boolean isReadonly() {
		return readonly;
	}
}
