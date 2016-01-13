package com.yimayhd.commission.controller;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commission.biz.ItemRebateBiz;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.model.query.ItemRebateQuery;
import com.yimayhd.marketing.client.model.domain.ItemRebateDO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/itemRebate")
public class ItemRebateController extends BaseController {

    @Autowired
    private ItemRebateBiz itemRebateBiz;


    @RequestMapping(value = "/queryItemRebate")
    public String queryItemRebate(Model model,ItemRebateQuery itemRebateQuery) throws Exception{

        PageVO<ItemRebateDO> pageVO = itemRebateBiz.queryItemRebate(itemRebateQuery);
        if(!CollectionUtils.isEmpty(pageVO.getItemList())){
            List<ItemRebateDO> commissionList =  pageVO.getItemList();
            model.addAttribute("itemRebateQuery", itemRebateQuery);
            model.addAttribute("pageVo",pageVO);
            model.addAttribute("itemRebateList",commissionList);
        }


        return "/system/commission/itemRebateSetting";
    }


    @RequestMapping(value = "/updateItemRebate")
    @ResponseBody
    public ResponseVo updateItemRebateRate(long itemId,int parentRate,int grandpaRate){

        System.out.println("itemId:"+itemId);
        System.out.println("parentRate:"+parentRate);
        System.out.println("grandpaRate:"+grandpaRate);
        return new ResponseVo(ResponseStatus.SUCCESS);
    }
}
