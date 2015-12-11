package com.yimayhd.harem.controller;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.TairManager;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.yimayhd.service.MessageCodeService;

/**
 *
 * @author
 */
@Controller
@RequestMapping("/view")
public class ViewEditController extends BaseController {

    @Autowired
    private CategoryService categoryServiceRef;
    @Autowired
    private TairManager tairManager;

    /**
     * 富文本编辑页面
     * @return 富文本编辑页面
     * @throws Exception
     */
    @RequestMapping(value = "/toViewEditTest", method = RequestMethod.GET)
    public
    String toViewEdit(Model model) throws Exception {
        CategoryResult categoryResult = categoryServiceRef.getCategory(1);
        System.out.println(categoryResult.getResultCode());
        model.addAttribute("message","请输入帐号和密码");
        return "/demo/editView";
    }

    /**
     * 富文本编辑页面
     * @return 富文本编辑页面
     * @throws Exception
     */
    @RequestMapping(value = "/toCalendar", method = RequestMethod.GET)
    public
    String toCalendarEdit(Model model) throws Exception {

        return "/demo/calendar";
    }

    /**
     * tair测试
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/testTair", method = RequestMethod.GET)
    public
    String testTair(Model model) throws Exception {
        int nameSpase = 299;
        HotelDO hotelDO = new HotelDO();
        hotelDO.setName("czf123");
        ResultCode a = tairManager.put(nameSpase, "czf100209", hotelDO);
        System.out.println(JSON.toJSONString(a));
        Result<DataEntry> b = tairManager.get(nameSpase, "czf100209");
        System.out.println(JSON.toJSONString(b));
        return "/demo/calendar";
    }
}
