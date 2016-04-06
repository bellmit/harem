package com.yimayhd.palace.model.query.apply;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.palace.base.BaseQuery;

public class ApplyQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;

    // 店铺名称
    private String merchantName;
    // 店铺ID
    private Long sellerId;
    // 店铺类型
    private int type;
    // 店铺负责人姓名
    private String principleName;
    // 店铺负责人联系方式
    private String principleTel;
    /**
     * 状态
     */
    private int status ;
    
	public String getMerchantName() {
		return StringUtils.isBlank(merchantName) ? null : merchantName ;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPrincipleName() {
		return StringUtils.isBlank(principleName) ? null : principleName;
	}
	public void setPrincipleName(String principleName) {
		this.principleName = principleName;
	}
	public String getPrincipleTel() {
		return principleTel;
	}
	public void setPrincipleTel(String principleTel) {
		this.principleTel = principleTel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSellerId() {
		return sellerId == null ? 0 : sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
}
