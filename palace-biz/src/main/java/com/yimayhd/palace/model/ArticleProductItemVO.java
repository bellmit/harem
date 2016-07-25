package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.List;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public class ArticleProductItemVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private long itemPrice;
	private String itemTitle;
	private String merchantName;
	private String merchantLogo;
	private String itemType;
	private String itemPic;
	private List<String> itemTagList;

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

	public List<String> getItemTagList() {
		return itemTagList;
	}

	public void setItemTagList(List<String> itemTagList) {
		this.itemTagList = itemTagList;
	}

	public long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(long itemPrice) {
		this.itemPrice = itemPrice;
	}

}
