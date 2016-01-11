package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 优惠券管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/voucherManage")
public class VoucherController extends BaseController {

    /**
     * 优惠券列表
     *
     * @return 优惠券列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, VoucherListQuery voucherListQuery) throws Exception {
        
        return "/system/live/list";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) throws Exception {
        
        return "/system/live/edit";
    }

    /**
     * 根据优惠券ID获取优惠券详情
     *
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        
        return "/system/live/edit";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(VoucherTemplateVO voucherTemplateVO) throws Exception {
       
        return "/success";
    }

    /**
     * 根据优惠券ID修改优惠券
     *
     * @return 优惠券
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO) throws Exception {
        
        return "/success";
    }
	
}
