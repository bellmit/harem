package com.yimayhd.palace.controller.poi;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.enums.ExportType;
import com.yimayhd.palace.model.enums.OrderShowStatus;
import com.yimayhd.palace.model.export.ExportAgent;
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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
            String fileName = "";
            Object objType = obj.get("type");
            ExportType eType = ExportType.getByType( Integer.parseInt(objType==null?"":objType.toString()) );
            if( null != eType ){
                if(ExportType.EXPORT_GF.getType() == eType.getType()){
                    fileName = handleExportGfOrder(obj, workbook);
                }else if(ExportType.EXPORT_MSG_MOBILE.getType() == eType.getType()){
                    fileName = handleExportMSGMobile(obj, workbook);
                }else if(ExportType.EXPORT_AGENT.getType() == eType.getType()){
                    fileName = handleExportAgentOrder(obj, workbook);
                }
            }
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

    public String handleExportMSGMobile(Map<String, Object> obj,HSSFWorkbook workbook){
        return null;
    }
    public String handleExportGfOrder(Map<String, Object> obj,HSSFWorkbook workbook){
        String filename =obj.get("fileName").toString();
        //这里可以创建多个sheet。
        HSSFSheet sheet = assemblyHSSFSheet(filename,workbook);
        //PageVO page = (PageVO)obj.get("pageVO");
        List<ExportGfOrder> list = (List<ExportGfOrder>)obj.get("ListExportGfOrder");
        if(null == list || list.size()==0){
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".csv";
        }

        for (int ii = 0; ii < list.size(); ii++) {
            int oo = 0;
            HSSFRow sheetRow = sheet.createRow(ii+1);
            ExportGfOrder entity = list.get(ii);
            sheetRow.createCell(oo).setCellValue(entity.getConsigneeName());
            sheetRow.createCell(++oo).setCellValue(entity.getBuyerName());
            sheetRow.createCell(++oo).setCellValue(entity.getBuyerPhoneNum());
            sheetRow.createCell(++oo).setCellValue(entity.getContactAddress());
            sheetRow.createCell(++oo).setCellValue(entity.getBizOrderId());
            sheetRow.createCell(++oo).setCellValue(entity.getItemTitle());
            sheetRow.createCell(++oo).setCellValue(entity.getItemId());
            sheetRow.createCell(++oo).setCellValue(entity.getItemPrice());
            sheetRow.createCell(++oo).setCellValue(entity.getActualFee());
            sheetRow.createCell(++oo).setCellValue(entity.getBuyAmount());
            sheetRow.createCell(++oo).setCellValue(entity.getCreateDate());
            sheetRow.createCell(++oo).setCellValue(entity.getSumFee());
            sheetRow.createCell(++oo).setCellValue(entity.getFreightFee());
            sheetRow.createCell(++oo).setCellValue(entity.getPaymentMode());
            sheetRow.createCell(++oo).setCellValue(entity.getOrderShowState());
            sheetRow.createCell(++oo).setCellValue(entity.getBuyerId());
            sheetRow.createCell(++oo).setCellValue(entity.getItemNumber());
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".csv";
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
    public String handleExportAgentOrder(Map<String, Object> obj,HSSFWorkbook workbook){
        String filename =obj.get("fileName").toString();
        //这里可以创建多个sheet。
        HSSFSheet sheet = assemblyAgentHSSFSheet(filename,workbook);
        //PageVO page = (PageVO)obj.get("pageVO");
        List<ExportAgent> list = (List<ExportAgent>)obj.get("ListExportAgent");
        if(null == list || list.size()==0){
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
        }

        for (int ii = 0; ii < list.size(); ii++) {
            int oo = 0;
            HSSFRow sheetRow = sheet.createRow(ii+1);
            ExportAgent entity = list.get(ii);
            sheetRow.createCell(oo).setCellValue(entity.getName());
            sheetRow.createCell(++oo).setCellValue(entity.getMobile());
            sheetRow.createCell(++oo).setCellValue(entity.getIdNum());
            sheetRow.createCell(++oo).setCellValue(entity.getWeixin());
            sheetRow.createCell(++oo).setCellValue(entity.getLicense());
            sheetRow.createCell(++oo).setCellValue(entity.getStatus());
            sheetRow.createCell(++oo).setCellValue(entity.getLevel());
            sheetRow.createCell(++oo).setCellValue(entity.getStartAt());
            sheetRow.createCell(++oo).setCellValue(entity.getEndAt());
            sheetRow.createCell(++oo).setCellValue(entity.getParentName());
            sheetRow.createCell(++oo).setCellValue(entity.getArea());
            sheetRow.createCell(++oo).setCellValue(entity.getCreatedAt());
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_"+filename+".xls";
    }
    public HSSFSheet assemblyAgentHSSFSheet(String filename,HSSFWorkbook workbook){
        HSSFSheet sheet = workbook.createSheet(filename);
        sheet.setDefaultColumnWidth((int) 12);

        int i = 0,o=0;
        HSSFCell cell = getCell(sheet, o, o);
        setText(cell, "代理商姓名");
        cell = getCell(sheet, o, ++i);
        setText(cell, "手机号");
        cell = getCell(sheet, o, ++i);
        setText(cell, "身份证号");
        cell = getCell(sheet, o, ++i);
        setText(cell, "微信号");
        cell = getCell(sheet, o, ++i);
        setText(cell, "授权号");
        cell = getCell(sheet, o, ++i);
        setText(cell, "状态");

        cell = getCell(sheet, o, ++i);
        setText(cell, "代理级别");
        cell = getCell(sheet, o, ++i);
        setText(cell, "授权开始日期");
        cell = getCell(sheet, o, ++i);
        setText(cell, "授权结束日期");
        cell = getCell(sheet, o, ++i);
        setText(cell, "推荐代理商姓名");
        cell = getCell(sheet, o, ++i);
        setText(cell, "代理地区");
        cell = getCell(sheet, o, ++i);
        setText(cell, "加入日期");
        return sheet;
}
}
