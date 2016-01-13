package com.yimayhd.commission.convert;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import net.pocrd.util.StringUtil;

import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.commission.client.param.AmountObtainDTO;
import com.yimayhd.commission.client.param.AmountTotalDetailDTO;
import com.yimayhd.commission.model.param.ExtractDTO;
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
	
	public static void extractConvert(final ExtractDTO srcDTO, final AmountObtainDTO desDTO){
		if(srcDTO == null || desDTO == null){
			return ;
		}
		String commissionAmt = srcDTO.getCommissionAmt();
		BigDecimal hundred = new BigDecimal(100);
		desDTO.setCommissionAmt(StringUtils.isBlank(commissionAmt) ? 0 : new BigDecimal(commissionAmt).multiply(hundred).longValue());
		desDTO.setDomainId(Domain.AZ.getType());
		desDTO.setFromId(srcDTO.getFromId());
		desDTO.setPayeeAccount(srcDTO.getPayeeAccount());
		desDTO.setTelNum(srcDTO.getTelNum());
		desDTO.setUserId(srcDTO.getUserId());
		desDTO.setUserName(srcDTO.getUserName());
	}
}
