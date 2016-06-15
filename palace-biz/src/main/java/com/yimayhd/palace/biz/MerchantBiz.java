package com.yimayhd.palace.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.result.BaseResult;

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
	
	public BaseResult<MerchantDO> getMerchantBySellerId(long userId){
		return merchantRepo.getMerchantBySellerId(userId);
	}
	
	/**
	 * 获取达人基本信息的服务类型
	 * @return 
	 * @return
	 */
	public  MemResult<List<CertificatesDO>> getServiceTypes() {
		return  merchantRepo.getServiceTypes();
	}
}
