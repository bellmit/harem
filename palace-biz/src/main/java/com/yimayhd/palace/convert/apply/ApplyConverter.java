package com.yimayhd.palace.convert.apply;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.ApplyVO;

public class ApplyConverter {
	
	public static List<ApplyVO> dto2ApplyVOs(List<ExamineInfoDTO> examineInfoDTOs){
		if(CollectionUtils.isEmpty(examineInfoDTOs)){
			return null;
		}
		List<ApplyVO> vos = new ArrayList<ApplyVO>();
		for( ExamineInfoDTO examineInfoDTO : examineInfoDTOs ){
			ApplyVO vo = dto2ApplyVO(examineInfoDTO);
			if( vo != null ){
				vos.add(vo);
			}
		}
		return vos ;
	}
	
	public static ApplyVO dto2ApplyVO(ExamineInfoDTO examineInfoDTO){
		if( examineInfoDTO == null ){
			return null ;
		}
		ApplyVO vo = new ApplyVO();
		vo.setAccountBankCity(examineInfoDTO.getAccountBankCity());
		vo.setAccountBankCityCode(examineInfoDTO.getAccountBankCityCode());
		vo.setAccountBankName(examineInfoDTO.getAccountBankName());
		vo.setAccountBankProvince(examineInfoDTO.getAccountBankProvince());
		vo.setAccountBankProvinceCode(examineInfoDTO.getAccountBankProvinceCode());
		vo.setAccountNum(examineInfoDTO.getAccountNum());
		vo.setAddress(examineInfoDTO.getAddress());
		vo.setAffairsCard(examineInfoDTO.getAffairsCard());
		vo.setArtCertificate(examineInfoDTO.getArtCertificate());
		vo.setBusinessLicense(examineInfoDTO.getBusinessLicense());
		vo.setClimbingCertificate(examineInfoDTO.getClimbingCertificate());
		vo.setCooperation1(examineInfoDTO.getCooperation1());
		vo.setCooperation2(examineInfoDTO.getCooperation2());
		vo.setCooperation3(examineInfoDTO.getCooperation3());
		vo.setCooperation4(examineInfoDTO.getCooperation4());
		vo.setCooperation5(examineInfoDTO.getCooperation5());
		vo.setCreateDate(examineInfoDTO.getCreateDate());
		vo.setDivingLinence(examineInfoDTO.getDivingLinence());
		vo.setDomainId(examineInfoDTO.getDomainId());
		vo.setDrivingLinence(examineInfoDTO.getDrivingLinence());
		vo.setExamineType( ExamineType.getByType(examineInfoDTO.getType()) );
		vo.setExaminStatus( ExamineStatus.getByStatus( examineInfoDTO.getExaminStatus()) );
		vo.setFinanceOpenBankId(examineInfoDTO.getFinanceOpenBankId());
		vo.setFinanceOpenBankName(examineInfoDTO.getFinanceOpenBankName());
		vo.setFinanceOpenName( examineInfoDTO.getFinanceOpenName() );
		vo.setId(examineInfoDTO.getId());
		vo.setLegralCardDown(examineInfoDTO.getLegralCardDown());
		vo.setLegralCardUp(examineInfoDTO.getLegralCardUp());
		vo.setLegralName(examineInfoDTO.getLegralName());
		vo.setMerchantName(examineInfoDTO.getSellerName() );
		vo.setOpenCard(examineInfoDTO.getOpenCard());
		vo.setOrgCard(examineInfoDTO.getOrgCard());
		vo.setPhotographyCertificate(examineInfoDTO.getPhotographyCertificate());
		vo.setPrincipleCard(examineInfoDTO.getPrincipleCard());
		vo.setPrincipleCardId(examineInfoDTO.getPrincipleCardId());
		vo.setPrincipleCardDown(examineInfoDTO.getPrincipleCardDown());
		vo.setPrincipleCardUp(examineInfoDTO.getPrincipleCardUp());
		vo.setPrincipleMail(examineInfoDTO.getPrincipleMail());
		vo.setPrincipleName(examineInfoDTO.getPrincipleName());
		vo.setPrincipleTel(examineInfoDTO.getPrincipleTel());
		vo.setSellerId(examineInfoDTO.getSellerId());
		vo.setSellerName(examineInfoDTO.getSellerName());
		vo.setTeacherCertificate(examineInfoDTO.getTeacherCertificate());
		vo.setTouchProve(examineInfoDTO.getTouchProve());
		vo.setTouristCard(examineInfoDTO.getTouristCard());
		vo.setTrainingCertificate(examineInfoDTO.getTrainingCertificate());
		vo.setTravelAgencyAuthorization(examineInfoDTO.getTravelAgencyAuthorization());
		vo.setTravelAgencyInsurance(examineInfoDTO.getTravelAgencyInsurance());
		vo.setTravingCard(examineInfoDTO.getTravingCard());
		return vo;
	}
	
	
}
