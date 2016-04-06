package com.yimayhd.palace.helper.apply;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.membercenter.client.dto.ExamineDealDTO;
import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.ApplyVO;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.user.client.domain.UserDO;

public class ApplyHelper {
	
	
	public static ExaminePageQueryDTO getExaminePageQueryDTO(ApplyQuery applyQuery){
		ExaminePageQueryDTO examinePageQueryDTO = new ExaminePageQueryDTO() ;
		if( applyQuery == null ){
			return examinePageQueryDTO;
		}
		examinePageQueryDTO.setPageNo(applyQuery.getPageNumber());
		examinePageQueryDTO.setPageSize(applyQuery.getPageSize());
		examinePageQueryDTO.setMerchantName(applyQuery.getMerchantName());
		examinePageQueryDTO.setPrincipleName(applyQuery.getPrincipleName());
//		examinePageQueryDTO.setPrincipleTel(applyQuery.getPrincipleTel());
		examinePageQueryDTO.setSellerId(applyQuery.getSellerId());
		examinePageQueryDTO.setType(applyQuery.getType());
//		examinePageQueryDTO.sete
		return examinePageQueryDTO ;
	}
	
	public static ExamineDealDTO getExamineDealDTO(ApproveVO approveVO){
		ExamineDealDTO examineDealDTO = new ExamineDealDTO() ;
		if( approveVO == null ){
			return examineDealDTO;
		}
		examineDealDTO.setCheckIsOk(approveVO.isPass());
		examineDealDTO.setExamineMes(approveVO.getReason());
		return examineDealDTO ;
	}
	
	public static List<Long> getSellerIds(List<ApplyVO> applyVOs){
		if( CollectionUtils.isEmpty(applyVOs) ){
			return null;
		}
		List<Long> ids = new ArrayList<Long>();
		for( ApplyVO applyVO : applyVOs ){
			long sellerId = applyVO.getSellerId();
			ids.add(sellerId);
		}
		return ids;
	}
	
	public static void fillUserInfos(List<ApplyVO> applyVOs, List<UserDO> userDOs){
		if( CollectionUtils.isEmpty(applyVOs) || CollectionUtils.isEmpty(userDOs) ){
			return ;
		}
		for( ApplyVO applyVO : applyVOs ){
			long sellerId = applyVO.getSellerId() ;
			for( UserDO userDO : userDOs ){
				long userId = userDO.getId() ;
				if( userId == sellerId ){
					String mobile = userDO.getMobileNo() ;
					applyVO.setApplierMobile(mobile);
					break;
				}
			}
		}
	}
}
