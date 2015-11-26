package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 酒店管理（商品）
 * @author czf
 */
@Controller
@RequestMapping("/B2C/comm/hotelManage")
public class CommHotelManageController extends BaseController {
    @Autowired
    private CommodityService commodityService;

    /**
     * 新增酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/comm/hotel/edit";
    }
    /**
     * 编辑酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        ItemDO itemDO = commodityService.getCommHotelById(id);
        model.addAttribute("commHotel",itemDO);

        return "/system/comm/hotel/edit";
    }

    /**
     * 编辑酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public
    String edit(ItemDO itemDO,@PathVariable("id") long id) throws Exception {
        itemDO.setId(id);
        commodityService.modifyCommHotel(itemDO);
        return "/success";
    }

    /**
     * 新增酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(ItemDO itemDO) throws Exception {
        commodityService.addCommHotel(itemDO);
        return "/success";
    }
}
