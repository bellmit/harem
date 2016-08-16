package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.helper.NumberFormatHelper;
import com.yimayhd.palace.model.enums.OrderShowStatus;
import com.yimayhd.palace.model.export.ExportGfOrder;
import com.yimayhd.palace.model.query.ExportQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.model.trade.SubOrder;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.controller.poi.ViewExcel;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.enums.PayChannel;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出功能
 */
@Controller
@RequestMapping("/export")
public class ExportController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemRepo itemRepo ;

    private AtomicInteger count = new AtomicInteger(0);
    private static final int MAX = 1 ;

    /**
     * 订单导出
     * @return gf订单导出
     * @throws Exception
     */
    @RequestMapping(value = "/gf/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model, ExportQuery exportQuery) throws Exception {
        try {
            if( count.intValue() >= MAX ){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","请等待其他用户导出完毕后再操作");
                mo.setViewName("error");
                return mo;
            }
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
            count.incrementAndGet() ;
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
            log.info("total count="+list.size()+",limit="+Constant.EXPORTMAXCOUNT);
            if(list.size()>Constant.EXPORTMAXCOUNT && !exportQuery.resolve){
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","订单数量"+pageVO.getTotalCount()+"条，单次导出最大数量不可超过"+Constant.EXPORTMAXCOUNT+"条，请分多次导出");
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
            //这里先取出来totalpage,如果等于1，那说明只有 EXPORTPAGESIZE[100]条数据，否则，去循环nextpage的数据，
            list.addAll(mainOrderToExportGfOrder(listMainOrder));
            int totalPage = pageVO.getLastPageNumber();
            if(1 == totalPage ){
               return  list;
            }
            for (int page=1;page<=totalPage;page++){
                page = page+1;
                exportQuery.setPageNumber(page);
                PageVO pv = getPageVOMainOrder(exportQuery);
                if(null == pv){
                    continue;
                }
                list.addAll(mainOrderToExportGfOrder(pv.getItemList()));
            }
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
        Map<Long,String> gyMap = new HashMap<Long,String>();
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
                    String buyer = bizOrder.getBuyerNick();
                    if(StringUtils.isEmpty(buyer)){
                        buyer = bizUser.getMobileNo();
                    }
                    eo.setBuyerName(buyer);
                    eo.setBizOrderId(bizOrderId);
                    eo.setCreateDate(DateUtil.dateToString(bizOrder.getGmtCreated(),"yyyy-MM-dd HH:mm:ss"));
                    eo.setActualFee(String.valueOf(NumUtil.moneyTransformDouble(BizOrderUtil.getSubOrderActualFee(subOrder.getBizOrderDO()))) );
                    eo.setSumFee(String.valueOf(NumUtil.moneyTransformDouble(bizOrder.getActualTotalFee())));

                }
                //if(null != bizUser){
                    //eo.setBuyerId(bizUser.getId());
                //}
                logisticsOrderDO = mainOrder.getLogisticsOrderDO();
                if(null != logisticsOrderDO ){
                    eo.setConsigneeName(logisticsOrderDO.getFullName());
                    StringBuilder sb = new StringBuilder();
                    if(StringUtils.isNotEmpty(logisticsOrderDO.getProv())){
                        sb.append(logisticsOrderDO.getProv());
                    }
                    if(StringUtils.isNotEmpty(logisticsOrderDO.getArea())){
                     sb.append(logisticsOrderDO.getArea());
                    }
                    if(StringUtils.isNotEmpty(logisticsOrderDO.getAddress())){
                        sb.append(logisticsOrderDO.getAddress());
                    }
                    eo.setContactAddress(sb.toString());
                    eo.setBuyerPhoneNum(logisticsOrderDO.getMobilePhone());
                    eo.setBuyerId(logisticsOrderDO.getBuyerId());
                }
                payOrderDO = mainOrder.getPayOrderDO();
                if(null != payOrderDO){
                    PayChannel payChannel = PayChannel.getPayChannel(payOrderDO.getPayChannel());
                    eo.setPaymentMode(null == payChannel ? "-" : payChannel.getDesc());
                }
                eo.setItemId(subOrder.getBizOrderDO().getItemId());
                eo.setItemTitle(subOrder.getBizOrderDO().getItemTitle());
                eo.setItemPrice(String.valueOf(NumUtil.moneyTransformDouble(subOrder.getBizOrderDO().getItemPrice())));
                eo.setBuyAmount(subOrder.getBizOrderDO().getBuyAmount());
                payOrderDO = mainOrder.getPayOrderDO();
                if(null != payOrderDO){
                    PayChannel py = PayChannel.getPayChannel(payOrderDO.getPayChannel());
                    eo.setPaymentMode(null == py?"":py.getDesc());
                }
                //-----------------
                eo.setFreightFee("0");//运费

                //查管易的编码
                long key = subOrder.getBizOrderDO().getItemId();
                String code = gyMap.get(key);
                if(StringUtils.isNotEmpty(code)){
                    eo.setItemNumber(code);
                }else{
                    List<ItemDO> itemResult = itemRepo.getItemByIds(Arrays.asList(key));
                    if(null != itemResult){
                        ItemDO ido = itemResult.get(0);
                        if(null != ido){
                            code = ido.getCode();
                            eo.setItemNumber(code);
                            gyMap.put(key,code);
                        }
                    }
                }
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
