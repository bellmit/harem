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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出功能
 */
@Controller
@RequestMapping("/GF/export")
public class ExportController extends BaseController{

    /**
     * 订单导出
     * @return 订单导出
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap model, ExportQuery exportQuery) throws Exception {
        model.put("query",exportQuery);
        model.put("fileName","GF订单列表");
        return new ModelAndView(new ViewExcel(), model);
    }
}
