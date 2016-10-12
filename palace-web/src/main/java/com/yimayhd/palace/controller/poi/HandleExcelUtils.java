package com.yimayhd.palace.controller.poi;

import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author create by yushengwei on 2016/10/12
 * @Description
 */
public class HandleExcelUtils {

    //读单行的没有标题的文件
    public List<String> readSingleLineNoSubject(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<String> list = new ArrayList<String>();
        String mobile = "";
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) { // 循环工作表Sheet
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {continue;}
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {// 循环行Row
                 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                 if (hssfRow != null) {
                     HSSFCell no = hssfRow.getCell(0);
                     mobile = getValue(no);
                     list.add(mobile);
                 }
            }
        }
        return list;
    }

    private String getValue(HSSFCell hssfCell) {
         if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) { // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
         } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) { // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
            } else {// 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
         }
    }

}
