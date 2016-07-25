package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.query.ExportQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.controller.poi.ViewExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出功能
 */
@Controller
@RequestMapping("/GF/export")
public class ExportController extends BaseController{
    @Autowired
    private OrderService orderService;

    /**
     * 订单导出
     * @return 订单导出
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model, ExportQuery exportQuery) throws Exception {
        try {
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
            PageVO<MainOrder> pageVO = orderService.getOrderList(exportQuery);
            if(null != pageVO && pageVO.getPageSize()>Constant.EXPORTPAGESIZE){//创建多sheet.依赖工具类无法注入问题的解决
                ModelAndView mo = new ModelAndView();
                mo.addObject("message","数据量太大，请缩短查询范围");
                mo.setViewName("error");
                return mo;
            }
            model.put("pageVO",pageVO);
            model.put("query",exportQuery);
            model.put("fileName","GF订单列表");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView(new ViewExcel(), model);
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
        }
        return flag;
    }

}
