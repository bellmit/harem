package com.yimayhd.commission.biz;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commission.model.query.ItemRebateQuery;
import com.yimayhd.commission.repo.ItemRebateRepo;
import com.yimayhd.marketing.client.model.domain.ItemRebateDO;
import com.yimayhd.marketing.client.model.enums.DomainType;
import com.yimayhd.marketing.client.model.param.ItemRebateRateUpdateDTO;
import com.yimayhd.marketing.client.model.result.SpmPageResult;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.palace.base.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/13
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class ItemRebateBiz {

    private static final Logger logger = LoggerFactory.getLogger(ItemRebateBiz.class);

    @Autowired
    private ItemRebateRepo itemRebateRepo;

    /**
     * 查询可分销的商品比例
     * @param itemRebateQuery
     * @return
     */
    public PageVO<ItemRebateDO> queryItemRebate(ItemRebateQuery itemRebateQuery){

        PageVO<ItemRebateDO> pageVO = null;

        try{
            int totalCount = 0;
            int pageNum = 0;
            int size = 0;
            List<ItemRebateDO> list = new ArrayList<ItemRebateDO>();

            com.yimayhd.marketing.client.model.query.ItemRebateQuery itemRebateQuery1 = new com.yimayhd.marketing.client.model.query.ItemRebateQuery();
            if(StringUtils.isNotBlank(itemRebateQuery.getItemTitle())){
                //itemRebateQuery1
            }
            SpmPageResult<ItemRebateDO> pageResult = itemRebateRepo.queryItemRebateResult(itemRebateQuery1);
            if(pageResult!=null && pageResult.isSuccess()){
                totalCount = pageResult.getTotalCount();
                list = pageResult.getList();
                pageNum = pageResult.getPageNo();
                size = pageResult.getPageSize();
            }
            pageVO = new PageVO<ItemRebateDO>(pageNum,size,totalCount,list);

        }catch (Exception e){
            logger.error("CommissionBiz.queryExtractDetailByUserId exception,params:{}", JSONObject.toJSONString(itemRebateQuery),e);
        }
        return pageVO;
    }


    public SpmResult<ItemRebateDO> updateItemRebate(ItemRebateRateUpdateDTO itemRebateRateUpdateDTO){

        SpmResult<ItemRebateDO> spmResult = null;

        try{

            if(itemRebateRateUpdateDTO != null){
                itemRebateRateUpdateDTO.setDomainId(DomainType.DOMAIN_MYTHIC_FLOW.getDomainId());
            }
            spmResult = itemRebateRepo.updateItemRebate(itemRebateRateUpdateDTO);
            if(spmResult != null && spmResult.isSuccess() && spmResult.getT()!=null){
                return spmResult;
            }

        }catch (Exception e){
            logger.error("CommissionBiz.updateItemRebate exception,params:{}", JSONObject.toJSONString(itemRebateRateUpdateDTO),e);
        }
        return spmResult;
    }


}
