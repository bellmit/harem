package com.yimayhd.palace.controller;

import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.helper.PromotionHelper;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.GiftActivityVO;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by p on 8/31/16.
 */
@Controller
@RequestMapping("/GF/giftActivityManage")
public class GFGiftActivityManageController {
    @Autowired
    private PromotionCommService promotionCommService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model, GiftActivityVO giftActivityVO) throws Exception {
//        giftActivityVO.setLotteryType(EntityType.SHOP.getType());
//        giftActivityVO.setType(7);
        PageVO<PromotionDO> pageVO = promotionCommService.getGiftActivtyList(giftActivityVO);
        model.addAttribute("promotionListQuery", giftActivityVO);
        model.addAttribute("pageVo",pageVO);

        return "/system/gfgiftactivity/list";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody GiftActivityVO giftActivityVO) throws Exception {
        promotionCommService.addGiftActivity(giftActivityVO);
        return "/success";
    }
    @RequestMapping(value="/toAdd", method = RequestMethod.GET)
    public String toAddGiftActivity(){
        return "/system/gfgiftactivity/add";
    }
    @RequestMapping("/edit/{id}")
    public String editGiftActivity(Model model, @PathVariable(value = "id") long id){
        return "/system/gfgiftactivity/edit";
    }
    @RequestMapping("/show/{id}")
    public String showGiftActivity(Model model, @PathVariable(value = "id") long id){
        return "/system/gfgiftactivity/show";
    }
    @RequestMapping("/toEdit")
    public void toEditGiftActivity(){
    }
}
