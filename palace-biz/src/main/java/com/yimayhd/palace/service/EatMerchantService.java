/**  
 * Project Name:palace-biz  
 * File Name:MerchantService.java  
 * Package Name:com.yimayhd.palace.service  
 * Date:2016年3月24日上午11:03:33  
 *  
*/  
  
package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.EatMerchantVO;
import com.yimayhd.palace.model.query.EatMerchantListQuery;

/**  
 * ClassName:MerchantService <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2016年3月24日 上午11:03:33 <br/>  
 * @author   zhangjian  
 * @version    
 * @see        
 */
public interface EatMerchantService {
	/**
	 * 
	 * saveEatMerchant:(新增美食商家). <br/>  
	 * @author zhangjian  
	 * @param eatMerchantVO
	 * @return  
	 *
	 */
	public EatMerchantVO addEatMerchant(EatMerchantVO eatMerchantVO);
	
	/**
	 * 
	 * updateEatMerchant:(更新美食商家信息). <br/>  
	 * @author zhangjian  
	 * @param eatMerchantVO
	 * @return  
	 *
	 */
	public boolean updateEatMerchant(EatMerchantVO eatMerchantVO);
	
	/**
	 * 
	 * batchDeleteEatMerchant:(批量删除美食商家). <br/>  
	 * @author zhangjian  
	 * @param eatMerchantIds
	 * @return  
	 *
	 */
	public boolean batchDeleteEatMerchant(List<Long> eatMerchantIds);
	
	/**
	 * 
	 * batchOfflineEatMerchant:(批量下架美食商家). <br/>  
	 * @author zhangjian  
	 * @param eatMerchantIds
	 * @return  
	 *
	 */
	public boolean batchOfflineEatMerchant(List<Long> eatMerchantIds);
	
	/**
	 * 
	 * queryEatMerchantList:(分页查询美食商家列表). <br/>  
	 * @author zhangjian  
	 * @return  
	 *
	 */
	public PageVO<EatMerchantVO> queryEatMerchantList(EatMerchantListQuery eatMerchantListQuery);
	
	/**
	 * 
	 * batchOnlineEatMerchant:(批量上架店铺). <br/>  
	 * @author zhangjian  
	 * @param eatMerchantIds
	 * @return  
	 *
	 */
	public boolean batchOnlineEatMerchant(List<Long> eatMerchantIds);
}
  
