package com.yimayhd.harem.model.vo;

import java.io.Serializable;

/**
  * @autuor : xusq
  * @date : 2015年11月30日
  * @description : 配置信息保存
  */
public class CfgBaseVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6285809936992791263L;
	
	private long boothId;
	
	private String boothCode;
	
	private long[] itemIds;
	
	private String[] imgUrl;
	
	private String subTitle;
	
	private String[] itemTitle;
	
	private String[] description;

	public long[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(long[] itemIds) {
		this.itemIds = itemIds;
	}

	public String[] getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String[] getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String[] itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public long getBoothId() {
		return boothId;
	}

	public void setBoothId(long boothId) {
		this.boothId = boothId;
	}

	public String getBoothCode() {
		return boothCode;
	}

	public void setBoothCode(String boothCode) {
		this.boothCode = boothCode;
	}

	
}
