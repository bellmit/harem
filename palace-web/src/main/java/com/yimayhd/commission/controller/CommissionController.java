package com.yimayhd.commission.controller;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commission.biz.CommissionBiz;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.model.query.CommissionDetailQuery;
import com.yimayhd.palace.base.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/commission")
public class CommissionController  {

    private static final Logger logger = LoggerFactory.getLogger(CommissionController.class);

    @Autowired
    private CommissionBiz commissionBiz;

    /**
     * 根据会员ID查询会员提现记录
     * @param model
     * @param commissionDetailQuery
     * @return
     */
    @RequestMapping(value = "/queryCommissionDetail")
    public String queryCommissionDetail(Model model,CommissionDetailQuery commissionDetailQuery){

        try{
            //测试
            commissionDetailQuery.setUserId(11);

            long userId = commissionDetailQuery.getUserId();
            int pageNo = commissionDetailQuery.getPageNumber();
            int pageSize = commissionDetailQuery.getPageSize();

            PageVO<AmountDetailDTO> pageVO =  commissionBiz.queryExtractDetailByUserId(userId, pageNo, pageSize);
            List<AmountDetailDTO> commissionList =  pageVO.getItemList();
            model.addAttribute("commissionDetailQuery", commissionDetailQuery);
            model.addAttribute("pageVo",pageVO);
            model.addAttribute("commissionList",commissionList);

            if(!CollectionUtils.isEmpty(commissionList)){
                AmountDetailDTO amountDetailDTO = commissionList.get(0);
                model.addAttribute("totalAmount",amountDetailDTO.getTotalMoney());
                model.addAttribute("userName",amountDetailDTO.getUserName());
            }

            return "/system/commission/commissionDetail";
        }catch (Exception e){
            logger.error("CommissionController.queryCommissionDetail error!params:{}", JSONObject.toJSONString(commissionDetailQuery),e);
            return "/error";
        }
    }


}
