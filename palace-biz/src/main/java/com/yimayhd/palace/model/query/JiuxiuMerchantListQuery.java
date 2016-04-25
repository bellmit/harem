package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * 
 */
public class JiuxiuMerchantListQuery extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantName;//商户名称
    private String merchantNo;//商户编号
    private String merchantType;//商户类型
   
 	private String merchantPrincipal; // 店铺负责人

 	private String merchantPrincipalTel;// 店铺负责人电话
 	
 	private String status;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getMerchantPrincipal() {
		return merchantPrincipal;
	}

	public void setMerchantPrincipal(String merchantPrincipal) {
		this.merchantPrincipal = merchantPrincipal;
	}

	public String getMerchantPrincipalTel() {
		return merchantPrincipalTel;
	}

	public void setMerchantPrincipalTel(String merchantPrincipalTel) {
		this.merchantPrincipalTel = merchantPrincipalTel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



   
	
	
}
