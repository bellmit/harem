/*
package com.yimayhd.palace.util.excel;

import com.suning.bi.manage.common.base.BaseException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PoiFor2007 extends ExcelFactory {

    */
/*
     * (non-Javadoc)
     * @see com.suning.bi.manage.common.util.ExcelFactory#excel2List(java.lang.Class, java.io.File, int, int)
     *//*

    */
/*@Override
    public List excel2List(Class clz, File file, int startRow, int startCol) {

        Field[] fds = clz.getDeclaredFields();
        List<Method> mls = new ArrayList<Method>();
        List<Field> fls = new ArrayList<Field>();

        List list = new ArrayList();
        StringBuffer msg = new StringBuffer();
        try {
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(ExceptField.class)) {
                    continue;
                }
                Method m = clz.getMethod(setMethodName(fd.getName()), fd.getType());
                mls.add(m);
                fls.add(fd);
            }

            Constructor constructor = clz.getDeclaredConstructor(new Class[0]);
            Workbook wb = new XSSFWorkbook(new FileInputStream(file));

            Object objNew = null;
            Object value = null;
            Field fd = null;
            // for (int i = 0, slen = wb.getNumberOfSheets(); i < slen; i++) {

            Sheet sheet = wb.getSheetAt(0);
            int clen = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int r = startRow, rlen = sheet.getPhysicalNumberOfRows(); r < rlen; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                objNew = constructor.newInstance(new Class[0]);
                for (int c = startCol, index = 0; c < clen; c++) {
                    fd = fls.get(index);
                    value = getCellValue(fd, row.getCell(c));
                    validateField(fd, value, r + 1, msg);
                    mls.get(index).invoke(objNew, value);
                    index++;
                }
                list.add(objNew);
            }
            // }
        } catch (Exception e) {
            throw new BaseException("Excel解析失败", e);
        }
        if (msg.length() > 0) {
            throw new ClientException(msg.toString());
        }
        return list;
    }*//*


    */
/*
     * (non-Javadoc)
     * @see com.suning.bi.manage.common.util.ExcelFactory#list2Excel(java.io.OutputStream, java.util.List, boolean)
     *//*

    @Override
    public void list2Excel(OutputStream os, List list, boolean isMerge) {

        if (list == null || list.size() == 0)
            return;

        Class clz = list.get(0).getClass();
        Field[] fds = clz.getDeclaredFields();

        List<Method> mls = new ArrayList();
        List<String> headers = new ArrayList();
        List<Integer> widths = new ArrayList();
        try {
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(ExportField.class)) {
                    Method m = clz.getMethod(getMethodName(fd.getName()));
                    mls.add(m);
                    ExportField exportField = fd.getAnnotation(ExportField.class);
                    widths.add(exportField.width());
                    if (exportField.header().length() > 0) {
                        headers.add(exportField.header());
                    } else if (fd.isAnnotationPresent(FieldDesc.class)) {
                        FieldDesc desc = fd.getAnnotation(FieldDesc.class);
                        headers.add(desc.value());
                    } else {
                        headers.add(fd.getName());
                    }
                }
            }

            Workbook wb = new XSSFWorkbook();
            Sheet st = null;
            Row row = null;
            Cell cell = null;
            Method md = null;

            for (int i = 0; i < list.size(); i++) {
                int rowNum = i % SHEET_NUM;
                if (rowNum == 0) {
                    st = wb.createSheet("sheet" + (i / SHEET_NUM + 1));
                    st.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
                    st.setDefaultRowHeight(DEFAULT_ROW_HEIGHT);
                    row = st.createRow(0);
                    for (int h = 0; h < headers.size(); h++) {
                        cell = row.createCell(h);
                        cell.setCellValue(headers.get(h));
                    }
                }

                row = st.createRow(rowNum + 1);
                for (int c = 0; c < mls.size(); c++) {
                    md = mls.get(c);
                    if (null == md.invoke(list.get(i))) {
                        continue;
                    }
                    cell = row.createCell(c);
                    cell.setCellValue(objectToString(md.invoke(list.get(i))));
                    if (isMerge && i % SHEET_NUM > 0) {
                        Object prev = md.invoke(list.get(i - 1));
                        Object current = md.invoke(list.get(i));
                        if ((prev == null && current == null)
                                || (prev != null && current != null && prev.equals(current))) {
                            st.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 1, c, c));
                        }
                    }
                }
            }

            wb.write(os);
        } catch (Exception e) {
            throw new BaseException("Excel创建失败", e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                throw new BaseException("关闭输出流失败", e);
            }
        }
    }

    */
/*
     * (non-Javadoc)
     * @see com.suning.bi.manage.common.util.ExcelFactory#mapList2Excel(java.io.OutputStream, java.util.List)
     *//*

    @Override
    public void mapList2Excel(OutputStream os, List<Map> list) throws Exception {

    }

    */
/**
     * 功能描述: <br>
     * 获取单元格值
     * 
     * @param fd 属性字段
     * @param cell 单元格
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     *//*

    private Object getCellValue(Field fd, Cell cell) {
        if (cell == null) {
            return null;
        }
        Class<?> clz = fd.getType();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                if (fd.isAnnotationPresent(ExcelType.class)) {
                    ExcelType type = fd.getAnnotation(ExcelType.class);
                    if (type.value() == Integer.class || type.value() == int.class) {
                        return convertPrimitive(clz, String.valueOf((int) cell.getNumericCellValue()));
                    }
                }
                return convertPrimitive(clz, String.valueOf(cell.getNumericCellValue()));
            default:
                return convertPrimitive(clz, cell.getStringCellValue());
        }
    }
}
*/
