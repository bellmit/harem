package com.yimayhd.harem.model.travel;

import java.util.List;

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
	private List<IdNameData> themes;// 主题
	private List<IdNameData> tags;// 标签
	private List<IdNameData> froms;// 出发地
	private List<IdNameData> tos;// 目的地
	private int publisherType;// 发布者类型
	private IdNameData publisher;// 发布者
	private String highlights;// 亮点
	private String recommond;// 代言
	private List<ExtraInfo> extraInfos;// 报名须知

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

	public List<IdNameData> getThemes() {
		return themes;
	}

	public void setThemes(List<IdNameData> themes) {
		this.themes = themes;
	}

	public List<IdNameData> getTags() {
		return tags;
	}

	public void setTags(List<IdNameData> tags) {
		this.tags = tags;
	}

	public List<IdNameData> getFroms() {
		return froms;
	}

	public void setFroms(List<IdNameData> froms) {
		this.froms = froms;
	}

	public List<IdNameData> getTos() {
		return tos;
	}

	public void setTos(List<IdNameData> tos) {
		this.tos = tos;
	}

	public int getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(int publisherType) {
		this.publisherType = publisherType;
	}

	public IdNameData getPublisher() {
		return publisher;
	}

	public void setPublisher(IdNameData publisher) {
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
