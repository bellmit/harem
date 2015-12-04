package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;

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
	private IdNamePair from;// 出发地
	private IdNamePair to;// 目的地
	private String publisherType;// 发布者类型
	private Map<String, String> publisher;// 发布者
	private String highlights;// 亮点
	private String recommond;// 代言
	private List<ExtraInfo> extraInfos;// 报名须知

	public BaseInfo() {
	}

	public BaseInfo(LineDO line, String routeCoverUrl, List<ComTagDO> comTagDOs) {
		// TODO YEBIN 数据对象生成展示对象
		this.id = line.getId();
		this.type = line.getType();
		this.name = line.getName();
		this.productImage = line.getCoverUrl();
		this.tripImage = routeCoverUrl;
		this.tags = new ArrayList<IdNamePair>();
		// 主题/tag别的接口

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

	public IdNamePair getFrom() {
		return from;
	}

	public void setFrom(IdNamePair from) {
		this.from = from;
	}

	public IdNamePair getTo() {
		return to;
	}

	public void setTo(IdNamePair to) {
		this.to = to;
	}

	public String getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}

	public Map<String, String> getPublisher() {
		return publisher;
	}

	public void setPublisher(Map<String, String> publisher) {
		this.publisher = publisher;
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

}
