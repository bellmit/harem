package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.enums.OrderShowStatus;
import com.yimayhd.palace.model.export.ExportGfOrder;
import com.yimayhd.palace.model.query.ExportQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.model.trade.SubOrder;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.controller.poi.ViewExcel;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.enums.PayChannel;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出功能
 */
@Controller
@RequestMapping("/GF/export")
public class ExportController extends BaseController{
    @Autowired
    private OrderService orderService;

    private AtomicInteger count = new AtomicInteger(0);
    private static final int MAX = 10 ;

    /**
     * 订单导出
     * @return 订单导出
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model, ExportQuery exportQuery) throws Exception {
        try {
            if( count.intValue() >= MAX ){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","请等待其他用户导出完毕后再操作");
                mo.setViewName("error");
                return mo;
            }
            count.incrementAndGet() ;

            if(null != exportQuery.getIds()){//暂时没有批量查的接口，等后期支持
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","暂时不支持批量导出");
                mo.setViewName("error");
                return mo;
            }
            if(!check(exportQuery)){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","请设置订单导出的查询条件");
                mo.setViewName("error");
                return mo;
            }
            exportQuery.setDomain(1100);
            exportQuery.setPageSize(Constant.EXPORTPAGESIZE);
            if(StringUtils.isNotEmpty(exportQuery.getItemName()) && exportQuery.getItemName().equals(",")){
                exportQuery.setItemName(null);
            }
            PageVO<MainOrder> pageVO = getPageVOMainOrder(exportQuery);
            if(null == pageVO){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","查询无数据");
                mo.setViewName("error");
                return mo;
            }
            List<ExportGfOrder> list = getListExportGfOrder(pageVO,exportQuery);
            if(list.size()>Constant.EXPORTMAXCOUNT){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","数据量太大【"+pageVO.getTotalCount()+"，"+list.size()+"】，请缩小查询范围");
                mo.setViewName("error");
                return mo;
            }
            model.put("pageVO",pageVO);
            model.put("query",exportQuery);
            model.put("ListExportGfOrder",list);
            model.put("fileName","GF订单列表");
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView mo = new ModelAndView();
            mo.addObject("message",e);
            mo.setViewName("error");
            return mo;
        }finally {
            count.decrementAndGet();
        }
        return new ModelAndView(new ViewExcel(), model);
    }

    public List<ExportGfOrder> getListExportGfOrder( PageVO<MainOrder> pageVO,ExportQuery exportQuery )throws Exception{
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        try {
            List<MainOrder> listMainOrder = pageVO.getItemList();
            if(null == pageVO || CollectionUtils.isEmpty(listMainOrder)){
                return list;
            }
            //这里先取出来totalpage,如果等于1，那说明只有100条数据，否则，去查nextpage的数据，一直循环
            list.addAll(mainOrderToExportGfOrder(listMainOrder));
            int totalPage = pageVO.getLastPageNumber();
            if(1 == totalPage ){
               return  list;
            }else {
                for (int page=1;page<=totalPage;page++){
                    page = page+1;
                    exportQuery.setPageNumber(page);
                    PageVO pv = getPageVOMainOrder(exportQuery);
                    if(null == pv){
                        continue;
                    }
                    list.addAll(mainOrderToExportGfOrder(pv.getItemList()));
                }
            }
            System.out.println("---"+ JSON.toJSONString(list));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public List<ExportGfOrder> mainOrderToExportGfOrder(List<MainOrder> mainOrderList) throws Exception{
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        for (MainOrder ma :mainOrderList ) {
        list.addAll(mainOrderToExportGfOrder(ma));
        }
        return list;
    }

    //子订单才成单个的订单
    public PageVO<MainOrder> getPageVOMainOrder(ExportQuery exportQuery){
        try {
            PageVO<MainOrder> pageVO = orderService.getExportOrderList(exportQuery);
            return pageVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<ExportGfOrder> mainOrderToExportGfOrder(MainOrder mainOrder) throws Exception{
        long bizOrderId = 0;
        PayOrderDO payOrderDO = null;
        LogisticsOrderDO logisticsOrderDO = null;
        OrderDetails orderDetails = null;
        ExpressVO expressVO = null;
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        BizOrderDO bizOrder = mainOrder.getBizOrderDO();
        UserDO bizUser = mainOrder.getUser();

        ExportGfOrder eo = null;
        List<SubOrder> subOrderList = mainOrder.getSubOrderList();
        if(CollectionUtils.isNotEmpty(subOrderList)){
            for (SubOrder subOrder:subOrderList) {
                eo = new ExportGfOrder();
                eo.setOrderShowState(OrderShowStatus.getByStatus(mainOrder.getOrderShowState()).getDes());
                if(null != bizOrder){
                    bizOrderId = bizOrder.getBizOrderId();
                    eo.setBuyerName(bizOrder.getBuyerNick());
                    eo.setActualFee(bizOrder.getActualTotalFee());
                    eo.setSumFee(bizOrder.getActualTotalFee());
                    eo.setBizOrderId(bizOrderId);
                    eo.setCreateDate(DateUtil.dateToString(bizOrder.getGmtCreated(),"yyyy-MM-dd"));
                }
                if(null != bizUser){
                    //eo.setBuyerId(bizUser.getId());
                }
                logisticsOrderDO = mainOrder.getLogisticsOrderDO();
                if(null != logisticsOrderDO ){
                    eo.setConsigneeName(logisticsOrderDO.getFullName());
                    eo.setContactAddress(logisticsOrderDO.getArea()+logisticsOrderDO.getAddress());
                    eo.setBuyerPhoneNum(logisticsOrderDO.getMobilePhone());
                    eo.setBuyerId(logisticsOrderDO.getBuyerId());
                }
                payOrderDO = mainOrder.getPayOrderDO();
                if(null != payOrderDO){
                    PayChannel payChannel = PayChannel.getPayChannel(payOrderDO.getPayChannel());
                    eo.setPaymentMode(null == payChannel ?"":payChannel.getDesc());
                }
                eo.setItemId(subOrder.getBizOrderDO().getItemId());
                eo.setItemTitle(subOrder.getBizOrderDO().getItemTitle());
                eo.setItemPrice(subOrder.getBizOrderDO().getItemPrice());
                eo.setBuyAmount(subOrder.getBizOrderDO().getBuyAmount());
                payOrderDO = mainOrder.getPayOrderDO();
                if(null != payOrderDO){
                    PayChannel py = PayChannel.getPayChannel(payOrderDO.getPayChannel());
                    eo.setPaymentMode(null == py?"":py.getDesc());
                }
                //-----------------
                eo.setFreightFee(1);
                list.add(eo);
            }
        }
        return list;
    }

    public boolean  check(ExportQuery exportQuery){
        boolean flag = false;
        if(null != exportQuery.getIds()){
            flag = true;
            return flag;
        }else if( StringUtils.isNotEmpty(exportQuery.getBeginDate()) && StringUtils.isNotEmpty(exportQuery.getEndDate())){
            flag = true;
            return flag;
        }else if(StringUtils.isNotEmpty(exportQuery.getItemName())){
            flag = true;
            return flag;
        }else if(StringUtils.isNotEmpty(exportQuery.getBuyerName())){
            flag = true;
            return flag;
        }else if(StringUtils.isNotEmpty(exportQuery.getBuyerPhone())){
            flag = true;
            return flag;
        }else if(StringUtils.isNotEmpty(exportQuery.getOrderNO())){
            flag = true;
            return flag;
        }else if(StringUtils.isNotEmpty(exportQuery.getOrderStat())){
            flag = true;
            return flag;
        }
        return flag;
    }

}
