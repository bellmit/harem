package com.yimayhd.commission.biz;

import com.yimayhd.commission.client.base.PageResult;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.repo.CommissionRepo;
import com.yimayhd.palace.base.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class CommissionBiz {

    private static final Logger logger = LoggerFactory.getLogger(CommissionBiz.class);

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
            List<AmountDetailDTO> list = new ArrayList<AmountDetailDTO>();
            PageResult<AmountDetailDTO> pageResult = commissionRepo.queryExtractDetailByUserId(userId, pageNo, pageSize);
            if(pageResult!=null && pageResult.isSuccess()){
                totalCount = pageResult.getTotalCount();
                list = pageResult.getList();
            }
            pageVO = new PageVO<AmountDetailDTO>(pageResult.getPageNo(),pageResult.getPageSize(),totalCount,list);

        }catch (Exception e){
            logger.error("CommissionBiz.queryExtractDetailByUserId exception,userId:",userId,e);
        }
        return pageVO;
    }
}
