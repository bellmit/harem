package com.yimayhd.palace.controller;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.activitycenter.query.ActPromotionPageQuery;
import com.yimayhd.activitycenter.result.ActResult;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.PromotionVO;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.palace.service.PromotionShopService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 店铺优惠管理
 * @author czf
 */
@Controller
@RequestMapping("/GF/promotionShopManage")
public class PromotionShopController extends BaseController {

    @Autowired
    private PromotionShopService promotionShopService;
    @Autowired
    private PromotionCommService promotionCommService;
    /**
     * 店铺优惠列表
     *
     * @return 店铺优惠列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        actPromotionPageQuery.setLotteryType(EntityType.SHOP.getType());
        PageVO<ActActivityDO> pageVO = promotionCommService.getList(actPromotionPageQuery);
        List<PromotionType> promotionTypeList = Arrays.asList(PromotionType.values());
        List<PromotionStatus> promotionStatusList = Arrays.asList(PromotionStatus.values());
        model.addAttribute("promotionListQuery",actPromotionPageQuery);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("promotionTypeList",promotionTypeList);
        model.addAttribute("promotionStatusList",promotionStatusList);
        return "/system/promotion/shop/list";
    }

    /**
     * 新增店铺优惠
     * @return 店铺优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model,int promotionType) throws Exception {
        model.addAttribute("promotionType",promotionType);
        return "/system/promotion/shop/edit";
    }

    /**
     * 根据优惠ID获取店铺优惠详情
     *
     * @return 店铺优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        //TODO
        //ActResult<ActPromotionDTO> result = promotionCommService.getById(id);
        //model.addAttribute("promotionVO",result.getT());
        return "/system/promotion/shop/edit";
    }

    /**
     * 新增优惠
     * @return 店铺优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(PromotionVO promotionVO) throws Exception {
        //promotionVO.setEntityType(EntityType.SHOP.getType());
        promotionShopService.add(promotionVO);
        return "/success";
    }

    /**
     * 根据优惠ID修改优惠
     *
     * @return 优惠
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,ActPromotionEditDTO promotionVO) throws Exception {
        //TODO
        //promotionCommService.modify(promotionVO);
        return "/success";
    }

    /**
     * 商品上架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo publish(@PathVariable("id") long id) throws Exception {
//		long sellerId = sessionManager.getUserId();
//		sellerId = B2CConstant.SELLERID;
//        long sellerId = B2CConstant.YIMAY_OFFICIAL_ID;
        return new ResponseVo();
    }

    /**
     * 商品下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        if (promotionCommService.close(id)){
            return new ResponseVo();
        }else {
            return new ResponseVo(ResponseStatus.ERROR);
        }
    }

}
