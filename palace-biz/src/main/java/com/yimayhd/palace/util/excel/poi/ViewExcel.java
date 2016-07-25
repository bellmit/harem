package com.yimayhd.palace.util.excel.poi;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.enums.OrderShowStatus;
import com.yimayhd.palace.model.export.ExportGfOrder;
import com.yimayhd.palace.model.query.ExportQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.SubOrder;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description
 */
public class ViewExcel extends AbstractExcelView {
    private static final Logger logger = LoggerFactory.getLogger(ViewExcel.class);

    @Override
    protected void buildExcelDocument(Map<String, Object> obj, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)throws Exception {
        OutputStream ouputStream = response.getOutputStream();
        try {
            String fileName = handleExportGfOrder(obj, workbook);
            fileName = PoiExcelUtils.encodeFilename(fileName, request);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName);
            workbook.write(ouputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ouputStream.flush();
            ouputStream.close();
        }

    }

    public List<ExportGfOrder> getListExportGfOrder(Map<String, Object> obj){
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        try {
            ExportQuery exportQuery = (ExportQuery)obj.get("query");
            PageVO<MainOrder> pageVO = (PageVO)obj.get("pageVO");
            if(null == pageVO || CollectionUtils.isEmpty(pageVO.getItemList())){
                return list;
            }
            for (MainOrder mo:pageVO.getItemList()) {
                list.addAll(mainOrderToExportGfOrder(mo));
            }
            System.out.println("---"+JSON.toJSONString(list));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public List<ExportGfOrder> mainOrderToExportGfOrder(MainOrder mainOrder){
        List<ExportGfOrder> list = new ArrayList<ExportGfOrder>();
        BizOrderDO bizOrder = mainOrder.getBizOrderDO();
        UserDO bizUser = mainOrder.getUser();
        if(null == bizOrder || null == bizUser){
            return null;
        }
        //一次性能搞出来的，需要批量查询的
        ExportGfOrder eo = null;
        List<SubOrder> subOrderList = mainOrder.getSubOrderList();
        if(CollectionUtils.isNotEmpty(subOrderList)){
            for (SubOrder subOrder:subOrderList) {
                eo = new ExportGfOrder();

                eo.setOrderShowState(OrderShowStatus.getByStatus(mainOrder.getOrderShowState()).getDes());
                eo.setBuyerName(bizOrder.getBuyerNick());
                eo.setActualFee(bizOrder.getActualTotalFee());

                eo.setBuyerId(bizUser.getId());
                eo.setBuyerPhoneNum(bizUser.getMobileNo());
                eo.setContactAddress(bizUser.getProvince()+bizUser.getCity());
                //eo.setConsigneeName("setConsigneeName");
                //eo.setFreightFee(1);
                //eo.setPaymentMode(bizOrder.getPayChannel());
                //eo.setSumFee(1);

                eo.setBizOrderId(bizOrder.getBizOrderId());
                eo.setCreateDate(DateUtil.dateToString(bizOrder.getGmtCreated(),"yyyy-MM-dd"));

                eo.setItemId(subOrder.getBizOrderDO().getItemId());
                eo.setItemTitle(subOrder.getBizOrderDO().getItemTitle());
                eo.setItemPrice(subOrder.getBizOrderDO().getItemPrice());
                eo.setBuyAmount(subOrder.getBizOrderDO().getBuyAmount());
                list.add(eo);
            }
        }
        return list;
    }

    public String handleExportGfOrder(Map<String, Object> obj,HSSFWorkbook workbook){
        String filename =obj.get("fileName").toString();
        HSSFSheet sheet = assemblyHSSFSheet(filename,workbook);
        PageVO page = (PageVO)obj.get("pageVO");
        List<ExportGfOrder> list = getListExportGfOrder(obj);
        if(null == list || list.size()==0){
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
        }

        for (int ii = 0; ii < list.size(); ii++) {
            int oo = 0;
            HSSFRow sheetRow = sheet.createRow(ii+1);
            ExportGfOrder entity = list.get(ii);
            oo = ++oo;
            sheetRow.createCell(oo).setCellValue(oo);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
    }


    public HSSFSheet assemblyHSSFSheet(String filename,HSSFWorkbook workbook){
        HSSFSheet sheet = workbook.createSheet(filename);
        sheet.setDefaultColumnWidth((int) 12);
        int i = 0,o=0;
        HSSFCell cell = getCell(sheet, o, o);
        setText(cell, "收货人姓名");
        cell = getCell(sheet, o, ++i);
        setText(cell, "买家");
        cell = getCell(sheet, o, ++i);
        setText(cell, "电话");
        cell = getCell(sheet, o, ++i);
        setText(cell, "详细地址");
        cell = getCell(sheet, o, ++i);
        setText(cell, "订单号");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品名称");

        cell = getCell(sheet, o, ++i);
        setText(cell, "商品ID");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品价格");
        cell = getCell(sheet, o, ++i);
        setText(cell, "实际支付金额");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品数量");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, ++i);
        setText(cell, "下单时间");
        cell = getCell(sheet, o, ++i);
        setText(cell, "订单总额");
        cell = getCell(sheet, o, ++i);
        setText(cell, "运费");
        cell = getCell(sheet, o, ++i);
        setText(cell, "支付方式");
        cell = getCell(sheet, o, ++i);
        setText(cell, "订单状态");
        cell = getCell(sheet, o, ++i);
        setText(cell, "买家ID");
        cell = getCell(sheet, o, ++i);
        setText(cell, "商品编码");
        return sheet;
    }


}
