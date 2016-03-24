/**  
 * Project Name:palace-biz  
 * File Name:MerchantListQuery.java  
 * Package Name:com.yimayhd.palace.model.query  
 * Date:2016年3月24日上午11:40:03  
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.  
 *  
*/

package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * ClassName:MerchantListQuery <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年3月24日 上午11:40:03 <br/>
 * 
 * @author zhangjian
 * @version
 * @see
 */
public class EatMerchantListQuery extends BaseQuery {
	/**  
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4420708458540613589L;
	private String name;
	private Integer cityCode;

	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
