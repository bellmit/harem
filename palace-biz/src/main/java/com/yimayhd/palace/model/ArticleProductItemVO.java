package com.yimayhd.palace.model;

import java.io.Serializable;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public class ArticleProductItemVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Float itemPrice;
	private String itemTitle;
	private String merchantName;
	private String merchantLogo;
	private String itemType;
	private String itemPic;
	private String itemPicTitle;

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantLogo() {
		return merchantLogo;
	}

	public void setMerchantLogo(String merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemPic() {
		return itemPic;
	}

	public void setItemPic(String itemPic) {
		this.itemPic = itemPic;
	}

	public String getItemPicTitle() {
		return itemPicTitle;
	}

	public void setItemPicTitle(String itemPicTitle) {
		this.itemPicTitle = itemPicTitle;
	}

}
