package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.query.base.CommodityListQuery;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
@Controller
@RequestMapping("/B2C/commodityManage")
public class CommodityManageController extends BaseController {
    @Autowired
    private CommodityService commodityService;
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
     * @return 酒店（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model) throws Exception {
        return "/system/hotel/edit";
    }
    /**
     * 编辑商品
     * @return 酒店（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        return "/system/hotel/edit";
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
}
