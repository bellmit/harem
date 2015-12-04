package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;

/**
 * 基本信息
 * 
 * @author yebin
 * 
 */
public class BaseInfo {
	private long id;// ID
	private int type;// 类型
	private String name;// 产品名称
	private String productImage;// 产品封面图
	private String tripImage;// 行程封面
	private List<IdNamePair> tags;// 标签
	private long fromId;// 出发地
	private long toId;// 目的地
	private int publisherType;// 发布者类型
	private long publisherId;// 发布者Id
	private String highlights;// 亮点
	private String recommond;// 代言
	private List<ExtraInfo> extraInfos;// 报名须知

	public BaseInfo() {
	}

	public BaseInfo(LineDO line, List<ComTagDO> comTagDOs) {
		this.id = line.getId();
		this.type = line.getType();
		this.name = line.getName();
		this.productImage = line.getCoverUrl();
		tags = new ArrayList<IdNamePair>();
		if (comTagDOs != null) {
			for (ComTagDO comTagDO : comTagDOs) {
				tags.add(new IdNamePair(comTagDO.getId(), comTagDO.getName()));
			}
		}
		this.fromId = line.getStartProvinceId();
		this.toId = line.getDestProvinceId();
		this.publisherType = line.getOwnerType();
		this.publisherId = line.getOwnerId();
		this.highlights = line.getDescription();
		this.recommond = line.getRecommend();
		this.extraInfos = new ArrayList<ExtraInfo>();
		this.extraInfos = new ArrayList<ExtraInfo>();
		if (StringUtils.isNotBlank(line.getNeedKnow())) {
			NeedKnow needKnow = JSON.parseObject(line.getNeedKnow(), NeedKnow.class);
			List<TextItem> textItems = needKnow.getFrontNeedKnow();
			if (textItems != null) {
				for (TextItem textItem : textItems) {
					this.extraInfos.add(new ExtraInfo(textItem.getTitle(), textItem.getContent()));
				}
			}
		}
	}

	/**
	 * 包含某个tag
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTag(String id) {
		if (StringUtils.isBlank(id) || tags == null || tags.size() <= 0) {
			return false;
		}
		for (IdNamePair tag : tags) {
			if (id.equals(tag.getId())) {
				return true;
			}
		}
		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getTripImage() {
		return tripImage;
	}

	public void setTripImage(String tripImage) {
		this.tripImage = tripImage;
	}

	public List<IdNamePair> getTags() {
		return tags;
	}

	public void setTags(List<IdNamePair> tags) {
		this.tags = tags;
	}

	public int getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(int publisherType) {
		this.publisherType = publisherType;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public String getRecommond() {
		return recommond;
	}

	public void setRecommond(String recommond) {
		this.recommond = recommond;
	}

	public List<ExtraInfo> getExtraInfos() {
		return extraInfos;
	}

	public void setExtraInfos(List<ExtraInfo> extraInfos) {
		this.extraInfos = extraInfos;
	}

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public long getToId() {
		return toId;
	}

	public void setToId(long toId) {
		this.toId = toId;
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}

}
