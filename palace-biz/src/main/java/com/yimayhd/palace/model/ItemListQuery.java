package com.yimayhd.palace.model;

import java.util.Date;

import com.yimayhd.palace.base.BaseQuery;

/**
 * 商品列表查询
 * 
 * @author hongfei.guo
 *
 */
public class ItemListQuery extends BaseQuery {
	private static final long serialVersionUID = -4699187319874706808L;
	private String name;// 商品名称
	private Long itemId;// 商品编码
	private Integer itemType;// 商品类型
	private Integer status;// 状态
	private Date beginDate;// 发布开始时间
	private Date endDate;// 发布结束时间
	private String merchantName; //商户名称
	private Long outId;//外部id
	private Integer outType;//外部类型 参考 ResourceType
	
	private String orderNumFilter;//商品权重
	private String sellerName;//店铺名称或者达人昵称
	private String nickName;
	private String shopName;
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	private long sellerId;
	
	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOutId() {
		return outId;
	}

	public ItemListQuery setOutId(Long outId) {
		this.outId = outId;
		return this;
	}

	public Integer getOutType() {
		return outType;
	}

	public ItemListQuery setOutType(Integer outType) {
		this.outType = outType;
		return this;
	}

	public String getOrderNumFilter() {
		return orderNumFilter;
	}

	public void setOrderNumFilter(String orderNumFilter) {
		this.orderNumFilter = orderNumFilter;
	}


	
}
