package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
@Controller
@RequestMapping("/B2C/commodity")
public class CommodityManageController extends BaseController {
    @Autowired
    private CommodityService commodityService;
    /**
     * 商品列表
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Long id) throws Exception {
        List<ItemDO> itemDOList = commodityService.getList();
        return "/system/comm/list";
    }
}
