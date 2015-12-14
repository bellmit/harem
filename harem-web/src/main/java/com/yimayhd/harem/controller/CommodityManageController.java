package com.yimayhd.harem.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.JsonResultUtil;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.query.CommodityListQuery;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
@Controller
@RequestMapping("/B2C/commodityManage")
public class CommodityManageController extends BaseController {

    private final static int ITEM_TYPE_HOTEL = 1;//酒店
    private final static int ITEM_TYPE_SCENIC = 2;//景区
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 商品列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,CommodityListQuery commodityListQuery) throws Exception {
        List<ItemDO> itemDOList = commodityService.getList();
        model.addAttribute("commodityList", itemDOList);
        model.addAttribute("commodityListQuery", commodityListQuery);
        return "/system/comm/list";
    }


    /**
     * 新增商品
     * @return 新增商品页
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model,long categoryId) throws Exception {
        CategoryDO categoryDO = categoryService.getCategoryById(categoryId);
        String redirectUrl = "";
        //TODO 对应的商品类型现在还没有，之后会提供
        int itemType = 10;
        switch(itemType){
            case ITEM_TYPE_HOTEL:
                redirectUrl = "/B2C/hotelManage/toAdd?categoryId=" + categoryId;//TODO 之后会把品类id或对应的属性传过去
                break;
            case ITEM_TYPE_SCENIC:
                redirectUrl = "/B2C/scenicSpotManage/toAdd?categoryId=" + categoryId;
                break;
            default:
                //普通商品，伴手礼应该也走普通商品
                model.addAttribute("category",categoryDO);
                return "/system/comm/common/edit";
        }
        return "redirect:" + redirectUrl;
    }
    /**
     * 编辑商品
     * @param model
     * @param itemId 商品ID
     * @param itemType 商品类型
     * @return  商品详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{itemId}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "itemId") long itemId,int itemType) throws Exception {
        String redirectUrl = "";
        switch (itemType) {
            case (ITEM_TYPE_HOTEL):
                redirectUrl = "/B2C/hotelManage/edit/" + itemId;
                break;
            case(ITEM_TYPE_SCENIC):
                redirectUrl = "/B2C/scenicSpotManage/edit/" + itemId;
                break;
            default:
                redirectUrl = "/B2C/commodityManage/common/edit/" + itemId;
                break;
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * 新增普通商品
     * @return 新增普通商品页
     * @throws Exception
     */
    @RequestMapping(value = "/common/toAdd", method = RequestMethod.GET)
    public
    String toAddCommon(Model model,long categoryId) throws Exception {
        CategoryDO categoryDO = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", categoryDO);
        return "";
    }

    /**
     * 编辑普通商品
     * @param model
     * @param itemId 商品ID
     * @param itemType 商品类型
     * @return  普通商品详情
     * @throws Exception
     */
    @RequestMapping(value = "/common/edit/{itemId}", method = RequestMethod.GET)
    public
    String toEditCommon(Model model,@PathVariable(value = "id") long itemId,int itemType) throws Exception {
        ItemResult itemResult = commodityService.getCommodityById(itemId);
        model.addAttribute("category", itemResult.getCategory());
        model.addAttribute("commodity", itemResult.getItem());
        model.addAttribute("itemSkuList", itemResult.getItemSkuDOList());
        return "";
    }



    /**
     * 商品状态变更
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setCommStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatus(@PathVariable("id") long id,int commStatus) throws Exception {
        commodityService.setCommStatus(id, commStatus);
        return new ResponseVo();
    }
    /**
     * 商品状态变更(批量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setCommStatusList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatusList(@RequestParam("commIdList[]")ArrayList<Long> commIdList,int commStatus) throws Exception {
        commodityService.setCommStatusList(commIdList, commStatus);
        return new ResponseVo();
    }

    /**
     * 商品分类页面
     * @return 商品分类
     * @throws Exception
     */
    @RequestMapping(value = "/toCategory", method = RequestMethod.GET)
    public
    String toCategory(Model model) throws Exception {
        List<CategoryDO> categoryDOList = categoryService.getCategoryDOList();
        model.addAttribute("categoryList",categoryDOList);
        return "/system/comm/category";
    }
    /**
     * 商品分类子类
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/subCategory/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public void getCategoryByParentId(HttpServletResponse response,@PathVariable("parentId") long parentId) throws Exception {
        List<CategoryDO> categoryDOList = categoryService.getCategoryDOList(parentId);

        for (int i = 0; i < categoryDOList.size(); i++) {
            categoryDOList.get(i).setParent(null);
            categoryDOList.get(i).setChildren(null);
        }
        String aaa = JSON.toJSONString(categoryDOList);
        JsonResultUtil.jsonResult(response, categoryDOList);
    }
}
