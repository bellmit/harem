package com.yimayhd.palace.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.RefundOrderVO;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.service.AfterSaleService;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.refund.client.enums.RefundType;
import com.yimayhd.refund.client.param.ExamineRefundOrderDTO;
import com.yimayhd.refund.client.query.RefundOrderQuery;
import com.yimayhd.refund.client.result.refundorder.ExamineRefundOrderResult;
import com.yimayhd.tradecenter.client.model.enums.RefundStatus;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * @author create by yushengwei on 2016/3/11
 * @Description：售后管理
 * @return $returns
 */

@Controller
@RequestMapping("/GF/aftersale")

public class AfterSaleManageController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired private AfterSaleService afterSaleService;

    @Autowired private SessionManager sessionManager;

    //list
    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model, RefundOrderQuery refundOrderQuery){
        int pageNumber = StringUtils.isEmpty(request.getParameter("pageNumber")) ? 1 : Integer.parseInt(request.getParameter("pageNumber")) ;
        String bizOrderIdBak = request.getParameter("bizOrderIdBak");
        if(StringUtils.isNotEmpty(bizOrderIdBak) ){//&& NumberUtils.isNumber(bizOrderIdBak)
            bizOrderIdBak=bizOrderIdBak.trim();
            refundOrderQuery.setBizOrderId(Long.parseLong(bizOrderIdBak));
        }
        try {
            refundOrderQuery.setPageNo(pageNumber);
            PageVO pageVo = getPageVo(refundOrderQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("orderList", pageVo.getItemList());
            model.addAttribute("orderListQuery", refundOrderQuery);
            model.addAttribute("bizType", refundOrderQuery.getBizType());
            return "/system/afterSale/gfAfterSaleList";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("gfOrderList|parameter="+ JSON.toJSONString(refundOrderQuery)+"|||exception="+e);
        }
        return "/error";

    }

    public PageVO getPageVo(RefundOrderQuery refundOrderQuery) throws Exception{
        refundOrderQuery.setDomain(1100);//TODO:enum类
        refundOrderQuery.setNeedCount(true);
        if(null == refundOrderQuery || refundOrderQuery.getBizType() == 0 ){
            refundOrderQuery.setBizType(RefundType.GF_REFUND.getType());//默认GF普通商品退款
        }
        PageVO<RefundOrderDO> pageVo = afterSaleService.queryRefundOrder(refundOrderQuery);
        return pageVo;
    }

    //详情
    @RequestMapping(value = "/refund/detail", method = RequestMethod.GET)
    public String detail(Model model, OrderListQuery orderListQuery,int type,int bizType) {
        String orderNO = orderListQuery.getOrderNO();
        if (StringUtils.isEmpty(orderNO)) {
            return "error";
        }
        RefundOrderVO refundOrderVO = afterSaleService.querySingRefundOrder(Long.parseLong(orderNO));
        if (null == refundOrderVO) {
            return "error";
        }
        model.addAttribute("bizType", bizType);
        model.addAttribute("orderShowState", refundOrderVO.getRefundOrderDO().getRefundStatus());
        model.addAttribute("refundOrderDO", refundOrderVO.getRefundOrderDO());
        model.addAttribute("refundOrderDetail", refundOrderVO.getOrderDetails());
        model.addAttribute("type", type);
        //区分是查看 还是审核，在根据状态跳转不同的页面
        if (type == 1) {
            model.addAttribute("approve", false);
            model.addAttribute("isModified", false);
        }else{
            model.addAttribute("isModified", true);
            int refundStatus = refundOrderVO.getRefundOrderDO().getRefundStatus();
            if(refundStatus == com.yimayhd.refund.client.enums.RefundStatus.CUSTOMER_RETURNS.getStatus() ){
            	model.addAttribute("agreeStatus", com.yimayhd.refund.client.enums.RefundStatus.SELLER_RECEIPT_AGREE.getStatus()) ;
            	model.addAttribute("rejectStatus", com.yimayhd.refund.client.enums.RefundStatus.SELLER_RECEIPT_REFUSE.getStatus()) ;
                return "/system/afterSale/shenhe_shouhuo";
            }
            model.addAttribute("approve", true);
        }
        return "/system/afterSale/aftersale_detail";
    }
    //审核
    @RequestMapping(value = "/refund/audit")
    @ResponseBody
    public ResponseVo audit(long refundOrderId, int refundStatus, HttpServletRequest request){
        String tkje = request.getParameter("stje");
        String shdz = request.getParameter("shdz");
        String auditorRemark = request.getParameter("auditorRemark");
        String[] pictures = request.getParameterValues("pictures");

        UserDO user = sessionManager.getUser();
        if(null == user){
            return new ResponseVo(ResponseStatus.UNAUTHORIZED);
        }
        ExamineRefundOrderDTO ero = new ExamineRefundOrderDTO();
        ero.setRefundStatus(refundStatus);
        ero.setAuditorId(user.getId());
        ero.setAuditorName(user.getName());
        ero.setAddress(shdz);
        ero.setRefundOrderId(refundOrderId);
        ero.setAuditorRemark(auditorRemark);
        if(StringUtils.isNotEmpty(tkje) ){//&& NumberUtils.isNumber(tkje)
            ero.setRefundActualFee( NumUtil.doubleToLong(Double.parseDouble(tkje)));//NumberUtils.toLong(tkje)
        }
        if(null != pictures && pictures.length>0){
            ero.setPictures(Arrays.asList(pictures));
        }
        ExamineRefundOrderResult result = afterSaleService.examineRefundOrder(ero);
        if(null == result ){
            return new ResponseVo(ResponseStatus.ERROR);
        }
        if(!result.isSuccess()){
            return new ResponseVo(-200,result.getResultMsg());
        }
        return new ResponseVo(ResponseStatus.SUCCESS);
    }

}
