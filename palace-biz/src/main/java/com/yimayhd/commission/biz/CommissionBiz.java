package com.yimayhd.commission.biz;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.client.base.BaseResult;
import com.yimayhd.commission.client.base.PageResult;
import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.commission.client.param.AmountObtainDTO;
import com.yimayhd.commission.client.param.AmountTotalDetailDTO;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.client.result.comm.AmountNumDTO;
import com.yimayhd.commission.client.result.comm.AmountTotalDTO;
import com.yimayhd.commission.convert.CommissionAmoutConvert;
import com.yimayhd.commission.model.query.CommissionListQuery;
import com.yimayhd.commission.repo.CommissionRepo;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.util.NumUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class CommissionBiz {

    private static final Logger logger = LoggerFactory.getLogger(CommissionBiz.class);

    private static final String AJAX_DEFAULT_ERROR_MSG = "服务请求发生异常";
    
    @Autowired
    private CommissionRepo commissionRepo;

    /**
     * 查询提现记录
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageVO<AmountDetailDTO> queryExtractDetailByUserId(long userId,int pageNo,int pageSize){

        PageVO<AmountDetailDTO> pageVO = null;

        try{
            int totalCount = 0;
            int pageNum = 0;
            int size = 0;
            List<AmountDetailDTO> list = new ArrayList<AmountDetailDTO>();
            PageResult<AmountDetailDTO> pageResult = commissionRepo.queryExtractDetailByUserId(userId, pageNo, pageSize);
            if(pageResult!=null && pageResult.isSuccess()){
                totalCount = pageResult.getTotalCount();
                list = pageResult.getList();
                pageNum = pageResult.getPageNo();
                size = pageResult.getPageSize();
            }
            pageVO = new PageVO<AmountDetailDTO>(pageNum,size,totalCount,list);

        }catch (Exception e){
            logger.error("CommissionBiz.queryExtractDetailByUserId exception,userId:",userId,e);
        }
        return pageVO;
    }
    
	public PageVO<AmountTotalDTO> getCommissionList(CommissionListQuery query) {
		logger.info("CommissionBiz.getCommissionList begin,param:" + JSON.toJSONString(query));
		PageVO<AmountTotalDTO> pageVO = null;
		
		try{
			if(query == null){
				logger.error("CommissionBiz.getCommissionList param null");
				query = new CommissionListQuery();
			}
			Integer pageNumber = query.getPageNumber();
			Integer pageSize = query.getPageSize();
			long domainId = query.getDomainId();
			
			if(pageNumber == null || pageNumber.intValue() == 0){
				logger.error("CommissionBiz.getCommissionList [pageNumber] param error,param:" + JSON.toJSONString(query));
				query.setPageNumber(BaseQuery.DEFAULT_PAGE);
			}
			if(pageSize == null || pageSize.intValue() == 0){
				logger.error("CommissionBiz.getCommissionList [pageSize] param error,param:" + JSON.toJSONString(query));
				query.setPageSize(BaseQuery.DEFAULT_SIZE);
			}
			if(domainId <= 0){
				logger.error("CommissionBiz.getCommissionList [domainId] param error,param:" + JSON.toJSONString(query));
				//TODO:domainId暂定默认为天香分销
				query.setDomainId(Domain.AZ.getType());
			}
			
			AmountTotalDetailDTO repoDTO = new AmountTotalDetailDTO();
			CommissionAmoutConvert.rebateAmtParamSetting(query, repoDTO);
			
			PageResult<AmountTotalDTO> remotePageResult = commissionRepo.queryRebateAmt(repoDTO);
			if(remotePageResult != null && remotePageResult.isSuccess() && CollectionUtils.isNotEmpty(remotePageResult.getList()) ){
				pageVO = new PageVO<AmountTotalDTO>(query.getPageNumber(), query.getPageSize(), 
						remotePageResult.getTotalCount(), remotePageResult.getList());
			}else{
				pageVO = new PageVO<AmountTotalDTO>(query.getPageNumber(), query.getPageSize(), 
						0);
			}		
			
		}catch(Exception e){
			logger.error("CommissionBiz.getCommissionList exceptions occur,param:{},ex:{}",
					JSON.toJSONString(query),e);
		}
		
		return pageVO;
	}
	
	/**
	 * 佣金转账提现
	 * @return
	 */
	public String amountExtract(AmountObtainDTO dto){
		
		logger.info("CommissionBiz.amountExtract begin,param:" + JSON.toJSONString(dto));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		
		try{
			BaseResult<AmountNumDTO> remoteBaseResult = commissionRepo.amountExtract(dto);
			if(remoteBaseResult == null || !remoteBaseResult.isSuccess()){
				map.put("success", false);
				map.put("desc", remoteBaseResult == null ?  AJAX_DEFAULT_ERROR_MSG : remoteBaseResult.getResultMsg());
				return JSON.toJSONString(map);
			}
			
		}catch(Exception e){
			logger.error("CommissionBiz.amountExtract exceptions occur,param:{},ex:{}",JSON.toJSONString(dto), e);
		}
		map.put("desc", "请求成功");
		return JSON.toJSONString(map);
	}
}
