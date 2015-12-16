package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.resourcecenter.model.enums.RegionLevel;

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
	private List<Long> tags;// 标签
	private long fromLevel;
	private long fromId;// 出发地
	private String fromName;
	private long toLevel;
	private long toId;// 目的地
	private String toName;
	private int publisherType;// 发布者类型
	private long publisherId;// 发布者Id
	private String highlights;// 亮点
	private MasterRecommend recommond;// 代言
	private List<TextItem> extraInfos;// 报名须知

	public BaseInfo() {
	}

	public BaseInfo(LineDO line, List<ComTagDO> comTagDOs) {
		this.id = line.getId();
		this.type = line.getType();
		this.name = line.getName();
		this.productImage = line.getCoverUrl();
		tags = new ArrayList<Long>();
		if (comTagDOs != null) {
			for (ComTagDO comTagDO : comTagDOs) {
				tags.add(comTagDO.getId());
			}
		}
		if (line.getStartProvinceId() > 0) {
			this.fromId = line.getStartProvinceId();
		} else if (line.getStartCityId() > 0) {
			this.fromId = line.getStartCityId();
		} else if (line.getStartTownId() > 0) {
			this.fromId = line.getStartTownId();
		}
		if (line.getDestProvinceId() > 0) {
			this.toId = line.getDestProvinceId();
		} else if (line.getDestCityId() > 0) {
			this.toId = line.getDestCityId();
		} else if (line.getDestTownId() > 0) {
			this.toId = line.getDestTownId();
		}
		this.publisherType = line.getOwnerType();
		this.publisherId = line.getOwnerId();
		this.highlights = line.getDescription();
		this.recommond = JSON.parseObject(line.getRecommend(), MasterRecommend.class);
		if (StringUtils.isNotBlank(line.getNeedKnow())) {
			NeedKnow needKnow = JSON.parseObject(line.getNeedKnow(), NeedKnow.class);
			this.extraInfos = needKnow.getFrontNeedKnow();
		}
	}

	/**
	 * 包含某个tag
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTag(long id) {
		if (id <= 0 || tags == null || tags.size() <= 0) {
			return false;
		}
		return tags.contains(id);
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

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
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

	public MasterRecommend getRecommond() {
		return recommond;
	}

	public void setRecommond(MasterRecommend recommond) {
		this.recommond = recommond;
	}

	public long getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(long fromLevel) {
		this.fromLevel = fromLevel;
	}

	public long getToLevel() {
		return toLevel;
	}

	public void setToLevel(long toLevel) {
		this.toLevel = toLevel;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public List<TextItem> getExtraInfos() {
		return extraInfos;
	}

	public void setExtraInfos(List<TextItem> extraInfos) {
		this.extraInfos = extraInfos;
	}

	public LineDO toLineDO() {
		LineDO line = new LineDO();
		line.setId(this.id);
		line.setType(this.type);
		line.setName(this.name);
		line.setCoverUrl(this.productImage);
		if (this.fromLevel == RegionLevel.PROVINCE.getLevel()) {
			line.setStartProvinceId(this.fromId);
		} else if (this.fromLevel == RegionLevel.CITY.getLevel()) {
			line.setStartCityId(this.fromId);
		} else if (this.fromLevel == RegionLevel.TOWN.getLevel()) {
			line.setStartTownId(this.fromId);
		}
		if (this.toLevel == RegionLevel.PROVINCE.getLevel()) {
			line.setDestProvinceId(this.toId);
		} else if (this.toLevel == RegionLevel.CITY.getLevel()) {
			line.setDestCityId(this.toId);
		} else if (this.toLevel == RegionLevel.TOWN.getLevel()) {
			line.setDestTownId(this.toId);
		}
		line.setOwnerType(this.publisherType);
		line.setOwnerId(this.publisherId);
		line.setDescription(this.highlights);
		line.setRecommend(JSON.toJSONString(this.recommond));
		NeedKnow needKnow = new NeedKnow();
		needKnow.setFrontNeedKnow(this.extraInfos);
		line.setNeedKnow(JSON.toJSONString(needKnow));
		return line;
	}
}
