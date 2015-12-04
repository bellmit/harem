package com.yimayhd.harem.model.travel;

/**
 * 套餐价格行
 * 
 * @author yebin
 *
 */
public class PackageBlock {
	private long price;
	private long stock;
	private long discount;

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}
}
