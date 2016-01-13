package com.yimayhd.commission.convert;

import com.yimayhd.commission.client.param.AmountTotalDetailDTO;
import com.yimayhd.commission.model.query.CommissionListQuery;

public class CommissionAmoutConvert {

	public static void rebateAmtParamSetting(final CommissionListQuery query,final AmountTotalDetailDTO repoDTO){
		if(query == null || repoDTO == null){
			return ;
		}
		repoDTO.setDomainId((int)query.getDomainId());
		repoDTO.setPageNo(query.getPageNumber());
		repoDTO.setPageSize(query.getPageSize());
		repoDTO.setTelNum(query.getTelNum());
		repoDTO.setUserName(query.getUserName());
	}
}
