//package com.yimayhd.palace.controller;
//
//import com.yimayhd.palace.base.BaseController;
//import com.yimayhd.palace.base.PageVO;
//import com.yimayhd.palace.model.query.PromotionListQuery;
//import com.yimayhd.palace.model.vo.PromotionVO;
//import com.yimayhd.palace.service.PromotionService;
//import com.yimayhd.promotion.client.domain.PromotionDO;
//import com.yimayhd.promotion.client.enums.EntityType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * 优惠管理
// * @author czf
// */
//@Controller
//@RequestMapping("/B2C/promotionManage")
//public class PromotionController extends BaseController {
//
//    @Autowired
//    private PromotionService promotionService;
//    /**
//     * 优惠列表
//     *
//     * @return 优惠列表
//     * @throws Exception
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String list(Model model, PromotionListQuery promotionListQuery) throws Exception {
//        PageVO<PromotionDO> pageVO = promotionService.getList(promotionListQuery);
//        model.addAttribute("promotionListQuery",promotionListQuery);
//        model.addAttribute("promotionList",pageVO.getItemList());
//        model.addAttribute("pageVo",pageVO);
//        return "/system/promotion/list";
//    }
//
//    /**
//     * 新增优惠
//     * @return 优惠详情
//     * @throws Exception
//     */
//    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
//    public String toAdd() throws Exception {
//        return "/system/promotion/edit";
//    }
//
//    /**
//     * 根据优惠ID获取优惠详情
//     *
//     * @return 优惠详情
//     * @throws Exception
//     */
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
//        PromotionVO promotionVO = promotionService.getById(id);
//        model.addAttribute("promotionVO",promotionVO);
//        return "/system/promotion/edit";
//    }
//
//    /**
//     * 新增优惠
//     * @return 优惠详情
//     * @throws Exception
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(PromotionVO promotionVO) throws Exception {
//        //promotionVO.setEntityType(EntityType.SHOP.getType());
//        promotionService.add(promotionVO);
//        return "/success";
//    }
//
//    /**
//     * 根据优惠ID修改优惠
//     *
//     * @return 优惠
//     * @throws Exception
//     */
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
//    public String edit(@PathVariable(value = "id") long id,PromotionVO promotionVO) throws Exception {
//        promotionVO.setId(id);
//        promotionService.modify(promotionVO);
//        return "/success";
//    }
//
//}
