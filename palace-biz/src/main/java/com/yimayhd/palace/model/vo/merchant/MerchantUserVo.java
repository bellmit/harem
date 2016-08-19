package com.yimayhd.palace.model.vo.merchant;

import com.yimayhd.user.client.dto.MerchantUserDTO;

public class MerchantUserVo extends MerchantUserDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regisPhone;

	public String getRegisPhone() {
		return regisPhone;
	}

	public void setRegisPhone(String regisPhone) {
		this.regisPhone = regisPhone;
	}
	
}
