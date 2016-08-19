package com.yimayhd.commission.convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.pocrd.util.StringUtil;

import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.commission.client.param.AmountObtainDTO;
import com.yimayhd.commission.client.param.AmountTotalDetailDTO;
import com.yimayhd.commission.client.param.UserInfoDTO;
import com.yimayhd.commission.client.result.comm.AmountTotalDTO;
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
		repoDTO.setUserName(query.getUserName());
		repoDTO.setPdcBankUser(query.getPdcBankUser());
		repoDTO.setPdcBankPhone(query.getPdcBankPhone());
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
	
	public static List<UserInfoDTO> userInfoDTOConvert(final List<AmountTotalDTO> srcList){
		if(CollectionUtils.isEmpty(srcList)){
			return null;
		}
		List<UserInfoDTO> desList = new ArrayList<UserInfoDTO>();
		long domainId = Domain.AZ.getType();
		for(AmountTotalDTO totalDTO : srcList){
			long userId = totalDTO.getUserId();
			if(userId <= 0){
				continue ;
			}
			String userName = totalDTO.getUserName();
			String pdcBankName = totalDTO.getPdcBankName();
			String pdcBankNo = totalDTO.getPdcBankNo();
			String pdcBankUser = totalDTO.getPdcBankUser();
			String pdcBankPhone = totalDTO.getPdcBankPhone();
			String memberIdNo = totalDTO.getMemberIdNo();
			if(
					   StringUtils.isNotBlank(userName)
					|| StringUtils.isNotBlank(pdcBankName) 
					|| StringUtils.isNotBlank(pdcBankNo)
					|| StringUtils.isNotBlank(pdcBankUser)
					|| StringUtils.isNotBlank(pdcBankPhone)
					|| StringUtils.isNotBlank(memberIdNo)
				){
				
				UserInfoDTO userInfoDTO = new UserInfoDTO();
				userInfoDTO.setDomainId((int)domainId);
				userInfoDTO.setUserId(userId);
				userInfoDTO.setUserName(userName);
				
				userInfoDTO.setPdcBankName(pdcBankName);
				userInfoDTO.setPdcBankNo(pdcBankNo);
				userInfoDTO.setPdcBankUser(pdcBankUser);
				userInfoDTO.setPdcBankPhone(pdcBankPhone);
				userInfoDTO.setMemberIdNo(memberIdNo);
				
				desList.add(userInfoDTO);
			}
		}
		return desList;
	}
}
