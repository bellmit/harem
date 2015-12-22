package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public abstract class BaseTravel {
	protected int categoryId;
	protected int options;
	protected BaseInfo baseInfo;// 基础信息
	protected PriceInfo priceInfo;// 价格信息

	public void init(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		// TODO YEBIN DO对象解析
		LineDO line = lineResult.getLineDO();
		if (line != null) {
			this.baseInfo = new BaseInfo(line, comTagDOs);
		}
		RouteDO route = lineResult.getRouteDO();
		if (route != null && this.baseInfo != null) {
			this.baseInfo.setTripImage(route.getPicture());
		}
		parseTripInfo(lineResult);
		this.priceInfo = new PriceInfo(lineResult.getItemDO(), lineResult.getItemSkuDOList());
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
	public LinePublishDTO toLinePublishDTO() {
		LinePublishDTO dto = new LinePublishDTO();
		if (baseInfo != null) {
			// TODO 转化baseInfo
			dto.setLineDO(baseInfo.toLineDO());
			setRouteInfo(dto);
		}
		if (priceInfo != null) {
			dto.setItemDO(this.getItemDO());
			dto.setItemSkuDOList(priceInfo.toItemSkuDOList(this.getSellerId()));
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
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, this.priceInfo.getLimitBySecond());
		itemFeature.put(ItemFeatureKey.AGREEMENT, this.priceInfo.getImportantInfosCode());
		itemDO.setItemFeature(itemFeature);
		itemDO.setItemType(ItemType.LINE.getValue());
		itemDO.setPayType(1);
		itemDO.setSource(1);
		itemDO.setVersion(1);
		itemDO.setOptions(options);
		itemDO.setCategoryId(categoryId);
		itemDO.setTitle(this.baseInfo.getName());
		itemDO.setStockNum(0);
		itemDO.setSubTitle("");
		itemDO.setOneWord("");
		itemDO.setDescription("");
		itemDO.setPicUrls(this.baseInfo.getTripImage());
		itemDO.setDetailUrl("");
		return itemDO;
	}
	
	protected long getSellerId() {
		long sellerId = 0;
		if(this.baseInfo!=null) {
			sellerId = this.baseInfo.getPublisherId();
		}
		return sellerId;
	}

	public abstract void setRouteInfo(LinePublishDTO dto);

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}
}
