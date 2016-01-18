package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.VoucherStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 优惠券管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/voucherManage")
public class VoucherController extends BaseController {

    @Autowired
    private VoucherTemplateService voucherTemplateService;
    /**
     * 优惠券列表
     *
     * @return 优惠券列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, VoucherListQuery voucherListQuery) throws Exception {
        PageVO<VoucherTemplateVO> pageVO = voucherTemplateService.getList(voucherListQuery);
        model.addAttribute("voucherListQuery",voucherListQuery);
        model.addAttribute("voucherTemplateList",pageVO.getItemList());
        model.addAttribute("pageVo",pageVO);
        return "/system/voucherTemplate/list";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() throws Exception {
        return "/system/voucherTemplate/edit";
    }

    /**
     * 根据优惠券ID获取优惠券详情
     *
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        VoucherTemplateVO voucherTemplateVO = voucherTemplateService.getById(id);
        voucherTemplateVO.setBeginDate(DateUtil.date2StringByDay(voucherTemplateVO.getStartTime()));
        voucherTemplateVO.setEndDate(DateUtil.date2StringByDay(voucherTemplateVO.getEndTime()));
        model.addAttribute("voucherDO",voucherTemplateVO);
        return "/system/voucherTemplate/edit";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(VoucherTemplateVO voucherTemplateVO) throws Exception {
        voucherTemplateVO.setEntityType(EntityType.SHOP.getType());
        voucherTemplateVO.setEntityId(10000);
        voucherTemplateVO.setStatus(VoucherStatus.ACTIVE.getStatus());
        voucherTemplateService.add(voucherTemplateVO);
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
        voucherTemplateVO.setId(id);
        voucherTemplateService.modify(voucherTemplateVO);
        return "/success";
    }
	
}
