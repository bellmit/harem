package com.yimayhd.palace.util.excel.poi;

import com.yimayhd.palace.model.export.ExportGfOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
            String fileName = handle( obj, workbook);
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

    /**
     * @Title: handle
     * @Description:(处理统计哪种数据的方法,将对应的数据组装成HSSFCell对象。)
     * @author create by yushengwei @ 2015年10月21日 上午10:28:14
     * @param @param type
     * @param @param obj
     * @param @param workbook
     * @param @return
     * @return String 返回类型
     * @throws
     */
    public String handle(Map<String, Object> obj, HSSFWorkbook workbook){
        List<ExportGfOrder> list1 = (List<ExportGfOrder>) obj.get("list");
        obj.put("list", list1);
        String name = handleExportGfOrder(obj, workbook);
        return name+"_数据汇总.xls";
    }




    public String handleExportGfOrder(Map<String, Object> obj, HSSFWorkbook workbook){
        String filename =obj.get("fileName").toString();

        HSSFSheet sheet = workbook.createSheet(filename);
        sheet.setDefaultColumnWidth((int) 12);
        int i = 0,o=0;
        HSSFCell cell = getCell(sheet, o, o);
        setText(cell, "收货人姓名");
        cell = getCell(sheet, o, i++);
        setText(cell, "买家");
        cell = getCell(sheet, o, i++);
        setText(cell, "电话");
        cell = getCell(sheet, o, i++);
        setText(cell, "详细地址");
        cell = getCell(sheet, o, i++);
        setText(cell, "订单号");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品名称");

        cell = getCell(sheet, o, i++);
        setText(cell, "商品ID");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品价格");
        cell = getCell(sheet, o, i++);
        setText(cell, "实际支付金额");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品数量");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品名称");
        cell = getCell(sheet, o, i++);
        setText(cell, "下单时间");
        cell = getCell(sheet, o, i++);
        setText(cell, "订单总额");
        cell = getCell(sheet, o, i++);
        setText(cell, "运费");
        cell = getCell(sheet, o, i++);
        setText(cell, "支付方式");
        cell = getCell(sheet, o, i++);
        setText(cell, "订单状态");
        cell = getCell(sheet, o, i++);
        setText(cell, "买家ID");
        cell = getCell(sheet, o, i++);
        setText(cell, "商品编码");



        List<ExportGfOrder> list = (List<ExportGfOrder>) obj.get("list");
        if(null == list || list.size()==0){
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
        }

        for (int ii = 0; ii < list.size(); ii++) {
            int oo = 0;
            HSSFRow sheetRow = sheet.createRow(ii+1);
            ExportGfOrder entity = list.get(ii);
            //sheetRow.createCell(0).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(entity.getCreateTime()));
            //int ty=entity.getActivityType()==null?0:entity.getActivityType();
            sheetRow.createCell(oo++).setCellValue(oo++);
            sheetRow.createCell(oo++).setCellValue(oo++);
            sheetRow.createCell(oo++).setCellValue(oo++);
            sheetRow.createCell(oo++).setCellValue(oo++);
            sheetRow.createCell(oo++).setCellValue(oo++);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
    }


}
