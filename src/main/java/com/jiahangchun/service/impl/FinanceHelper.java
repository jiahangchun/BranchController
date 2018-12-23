package com.jiahangchun.service.impl;

import com.jiahangchun.DO.FinanceChangeDO;
import com.jiahangchun.common.FinanceChangeBsTypeEnum;
import com.jiahangchun.common.FinanceChangeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午4:19
 **/
@Slf4j
public class FinanceHelper {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断Excel的版本,获取Workbook
     */
    public static Workbook getWorkBok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public static void checkExcelValid(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 获取值
     *
     * @param cell
     * @return
     */
    private static String getValue(Cell cell) {
        Object obj = null;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                obj = " ";
                break;
        }
        String value = String.valueOf(obj);
        return value.replace("`", "");
    }


    /**
     * 填充财务报表记录
     *
     * @return
     */
    public static FinanceChangeDO fillFinanceChangDO(Row row) {
        FinanceChangeDO financeChangeDO = new FinanceChangeDO();
        try {
            int end = row.getLastCellNum();
            for (int i = 0; i < end; i++) {
                Cell cell = row.getCell(i);
                String value = getValue(cell);
                switch (i) {
                    case 0:
                        try {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMAT);
                            DateTime dateTime = dateTimeFormatter.parseDateTime(value);
                            Date time = dateTime.toDate();
                            financeChangeDO.setTime(time);
                        } catch (Exception e) {
                            log.error("未识别的日期类型" + row.toString());
                        }
                        break;
                    case 1:
                        financeChangeDO.setTradeSn(value);
                        break;
                    case 2:
                        financeChangeDO.setFinanceSn(value);
                        break;
                    case 3:
                        financeChangeDO.setName(value);
                        break;
                    case 4:
                        financeChangeDO.setBsType(FinanceChangeBsTypeEnum.getEnumByValue(value).getValue());
                        break;
                    case 5:
                        financeChangeDO.setType(FinanceChangeTypeEnum.getEnumByValue(value).getValue());
                        break;
                    case 6:
                        financeChangeDO.setCount(Long.valueOf(value.replace(".", "")));
                        break;
                    case 7:
                        financeChangeDO.setLeftCount(Long.valueOf(value.replace(".", "")));
                        break;
                    case 8:
                        financeChangeDO.setApplyName(value);
                        break;
                    case 9:
                        financeChangeDO.setRemark(value);
                        break;
                    case 10:
                        financeChangeDO.setBsSn(value);
                        break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return financeChangeDO;
    }

    /**
     * 输出人员看的财务
     *
     * @param count
     * @return
     */
    public static String changeFinance(Long count) {
        if (count == null) {
            count = 0L;
        }
        return (count / 100) + "." + (count % 100);
    }
}
