package com.yimayhd.palace.model;

import java.io.Serializable;

/**
 * 达人故事资源项
 * 
 * @author xiemingna
 *
 */
public class ArticleHotelResourceItemVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 资源封面
	 */
	private String resourcePic;
	/**
	 * 资源名称
	 */
	private String resourceName;
	/**
	 * 资源所在地区
	 */
	private String tradeArea;
	/**
	 * 最低价
	 */
	private long resourcePrice;

	public String getResourcePic() {
		return resourcePic;
	}

	public void setResourcePic(String resourcePic) {
		this.resourcePic = resourcePic;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public long getResourcePrice() {
		return resourcePrice;
	}

	public void setResourcePrice(long resourcePrice) {
		this.resourcePrice = resourcePrice;
	}

	public String getTradeArea() {
		return tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
	}

}
