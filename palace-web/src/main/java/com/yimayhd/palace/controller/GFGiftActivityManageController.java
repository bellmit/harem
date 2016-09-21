package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.*;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.helper.PromotionHelper;
import com.yimayhd.palace.model.*;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.springframework.beans.TypeMismatchException;
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
        actPromotionPageQuery.setType(PromotionType.FREE_GIFT.getType());
        PageVO<ActActivityPromotionDO> pageVO = promotionCommService.getList(actPromotionPageQuery);
        List<PromotionStatus> promotionStatusList = Arrays.asList(PromotionStatus.values());
        model.addAttribute("promotionListQuery", actPromotionPageQuery);
        model.addAttribute("pageVo", pageVO);
        model.addAttribute("promotionStatusList",promotionStatusList);
        return "/system/gfgiftactivity/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo add(@RequestBody ActActivityEditVO actActivityEditVO) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        try {
            Boolean result = promotionCommService.addGift(actActivityEditVO);
            if(result){
                responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
            } else {
                responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
            }
        } catch (Exception e){
            responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
            responseVo.setMessage(e.getMessage());
        }
        return responseVo;
    }

    @RequestMapping(value="/toAdd", method = RequestMethod.GET)
    public String toAddGiftActivity(){
        return "/system/gfgiftactivity/edit";
    }

    @RequestMapping("/edit/{id}")
    public String toEditGiftActivity(Model model, @PathVariable(value = "id") long id){
        try {
            ActActivityEditVO actActivityEditVO = promotionCommService.getGiftById(id);
            model.addAttribute("actActivityEditVO", actActivityEditVO);
            return "/system/gfgiftactivity/edit";
        } catch (Exception e) {
            e.printStackTrace();
            return "/error";
        }
    }

    /**
     * 下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        boolean result = promotionCommService.close(id);
        if(result) {
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
        }
        return responseVo;
    }

    /**
     * 修改结束时间
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo endTime(@RequestBody ActActivityVO actActivityVO) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        try {
            Boolean reuslt = promotionCommService.updateGiftEndTime(actActivityVO);
            if(reuslt) {
                responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
            } else {
                responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
            }

        } catch (Exception e) {
            responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
        }
        return responseVo;
    }

    @RequestMapping("/show/{id}")
    public String showGiftActivity(Model model, @PathVariable(value = "id") long id){
        try {
            ActActivityEditVO actActivityEditVO = promotionCommService.getGiftById(id);
            model.addAttribute("actActivityEditVO", actActivityEditVO);
            return "/system/gfgiftactivity/show";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }
    @RequestMapping("/check")
    @ResponseBody
    public ResponseVo check(@RequestBody ActActivityVO actActivityVO){
        ResponseVo responseVo = new ResponseVo();
        Boolean result = promotionCommService.checkGift(actActivityVO);
        if(result) {
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setStatus(ResponseStatus.UNSUCCESSFUL.VALUE);
        }
        return responseVo;
    }
}
