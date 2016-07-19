package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.model.export.ExportGfOrder;
import com.yimayhd.palace.model.query.ExportQuery;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.util.excel.poi.ViewExcel;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出功能
 */
@Controller
@RequestMapping("/GF/export")
public class ExportController extends BaseController{

    @Autowired ViewExcel viewExcel;

    /**
     * 订单导出
     * @return 订单导出
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,ExportQuery exportQuery) throws Exception {
        Map<String, Object> obj = new HashMap<String,Object>();
        obj.put("fileName","GF订单列表");
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        ExportGfOrder e1 = new ExportGfOrder();
        ExportGfOrder e2 = new ExportGfOrder();
        ExportGfOrder e3 = new ExportGfOrder();
        ExportGfOrder e4 = new ExportGfOrder();
        ExportGfOrder e5 = new ExportGfOrder();




        viewExcel.handle();
        return null;
    }
}
