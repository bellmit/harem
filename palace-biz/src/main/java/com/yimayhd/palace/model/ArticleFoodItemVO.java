package com.yimayhd.palace.model;

import java.io.Serializable;

/**
 * 达人故事美食
 * 
 * @author xushubing
 *
 */
public class ArticleFoodItemVO implements Serializable {

	private static final long serialVersionUID = 4089623232022825745L;
	// 店铺id
	private long id;

	// 店铺名称
	private String name;

	private String image;
	private long avgPrice;

	private String service;

	private String top;
	private long sellerId;

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(long avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}
