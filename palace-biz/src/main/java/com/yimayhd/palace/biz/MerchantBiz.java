package com.yimayhd.palace.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.result.BizResultSupport;

/**
 * 
 * @author zhangxy
 *
 */
public class MerchantBiz {

	@Autowired
	private MerchantRepo merchantRepo;
	public BizResultSupport addDeliciousFood(MerchantVO vo) {
		return merchantRepo.addDeliciousFood(vo);
		
		
	}
	
	public BizResultSupport updateDeliciousFood(MerchantVO vo) {
		return merchantRepo.updateDeliciousFood(vo);
	}
	public BizResultSupport batchUpdateMerchant(List<Long> idList,int status) {
		return merchantRepo.batchUpdateMerchant(idList, status);
	}
}
