package com.yimayhd.harem.model.travel;

import java.util.List;
import java.util.Map;

/**
 * 基本信息
 * 
 * @author yebin
 * 
 */
public class BaseInfo {
	private long id;// ID
	private int type;// 类型
	private String productName;// 产品名称
	private String productImage;// 产品封面图
	private String tripImage;// 行程封面
	private List<Map<String, String>> themes;// 主题
	private List<Map<String, String>> tags;// 标签
	private Map<String, String> from;// 出发地
	private Map<String, String> to;// 目的地
	private String publisherType;// 发布者类型
	private Map<String, String> publisher;// 发布者
	private String highlights;// 亮点
	private String recommond;// 代言
	private List<ExtraInfo> extraInfos;// 报名须知

	/**
	 * 包含某个tag
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTag(String id) {
		if (tags == null || tags.size() <= 0) {
			return false;
		}
		for (Map<String, String> tag : tags) {
			if (tag.containsKey("id") && tag.get("id").equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 包含某个theme
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTheme(String id) {
		if (themes == null || themes.size() <= 0) {
			return false;
		}
		for (Map<String, String> theme : themes) {
			if (theme.containsKey("id") && theme.get("id").equals(id)) {
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public List<Map<String, String>> getThemes() {
		return themes;
	}

	public void setThemes(List<Map<String, String>> themes) {
		this.themes = themes;
	}

	public List<Map<String, String>> getTags() {
		return tags;
	}

	public void setTags(List<Map<String, String>> tags) {
		this.tags = tags;
	}

	public Map<String, String> getFrom() {
		return from;
	}

	public void setFrom(Map<String, String> from) {
		this.from = from;
	}

	public Map<String, String> getTo() {
		return to;
	}

	public void setTo(Map<String, String> to) {
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
