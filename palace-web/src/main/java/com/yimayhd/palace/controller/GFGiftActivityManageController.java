package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.helper.PromotionHelper;
import com.yimayhd.palace.model.*;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by p on 8/31/16.
 */
@Controller
@RequestMapping("/GF/giftActivityManage")
public class GFGiftActivityManageController {
    @Autowired
    private PromotionCommService promotionCommService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        actPromotionPageQuery.setLotteryType(EntityType.SHOP.getType());
        actPromotionPageQuery.setType(7);
        PageVO<ActActivityPromotionDO> pageVO = promotionCommService.getList(actPromotionPageQuery);
        List<PromotionStatus> promotionStatusList = Arrays.asList(PromotionStatus.values());
        model.addAttribute("promotionListQuery", actPromotionPageQuery);
        model.addAttribute("pageVo", pageVO);
        model.addAttribute("promotionStatusList",promotionStatusList);
        return "/system/gfgiftactivity/list";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean add(@RequestBody GiftActivityVO giftActivityVO) throws Exception {
        return promotionCommService.addGiftActivity(giftActivityVO);
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Boolean editGiftActivity(@RequestBody GiftActivityVO giftActivityVO) throws Exception{
        return promotionCommService.updateGiftActivity(giftActivityVO);
    }
    @RequestMapping(value="/toAdd", method = RequestMethod.GET)
    public String toAddGiftActivity(){
        return "/system/gfgiftactivity/add";
    }
    @RequestMapping("/toEdit/{id}")
    public String toEditGiftActivity(Model model, @PathVariable(value = "id") long id){
        try {
            ActActivityEditVO actActivityEditVO = promotionCommService.getById(id);
            List<PromotionVO> promotionVOs = actActivityEditVO.getPromotionVOList();
            PromotionVO promotionVO = promotionVOs.get(0);
            promotionVO.getFeature();
            Map<String, List<GiftVO>> hashMap = JSON.toJavaObject(promotionVO.getFeature(), Map<String, List<GiftVO>>);
            model.addAttribute("promotionVO", promotionVO);
            return "/system/gfgiftactivity/edit";
        } catch (Exception e) {
            e.printStackTrace();
            return "/error";
        }
    }
    @RequestMapping("/show/{id}")
    public String showGiftActivity(Model model, @PathVariable(value = "id") long id){
        return "/system/gfgiftactivity/show";
    }
    @RequestMapping("/toEdit")
    public void toEditGiftActivity(){
    }
}
