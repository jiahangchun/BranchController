package com.jiahangchun.service.impl;

import com.jiahangchun.DO.FinanceChangeDO;
import com.jiahangchun.DO.FinanceChangeQTO;
import com.jiahangchun.DO.InputFileDO;
import com.jiahangchun.DO.TaskDO;
import com.jiahangchun.common.FinanceChangeBsTypeEnum;
import com.jiahangchun.common.FinanceChangeTypeEnum;
import com.jiahangchun.common.InputFileStatusEnum;
import com.jiahangchun.repository.FinanceChangeRepository;
import com.jiahangchun.repository.InputFileRepository;
import com.jiahangchun.repository.TaskRepository;
import com.jiahangchun.service.finance.FinanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午2:57
 **/
@Service
@Slf4j
public class FinanceServiceImpl implements FinanceService {
    /**
     * 默认允许的后缀
     */

    private static final Integer IGNORE_ROW = 1;

    private static final String DEFAULT_SHEET_NAME = "财务导出表";
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssS");

    @Autowired
    private AliyunOssManager aliyunOssManager;
    @Autowired
    private FinanceChangeRepository financeChangeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private InputFileRepository inputFileRepository;

    @Override
    public Boolean saveFile(String fileName) {
        saveToSql(fileName);
        return Boolean.TRUE;
    }

    @Async
    public void saveToSql(String fileName) {
        Long fileId = 0L;
        try {
            File excelFile = new File(fileName); // 创建文件对象 "/Users/hzmk/Desktop/模板文件.xlsx"
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            FinanceHelper.checkExcelValid(excelFile);
            fileId = saveInputFile(fileName);

            Workbook workbook = FinanceHelper.getWorkBok(in, excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            int count = 0;
            for (Row row : sheet) {
                try {
                    if (count < IGNORE_ROW) {
                        count++;
                        continue;
                    }
                    //如果当前行没有数据，跳出循环
                    if (row.getCell(0).toString().equals("")) {
                        continue;
                    }
                    FinanceChangeDO financeChangeDO = FinanceHelper.fillFinanceChangDO(row);
                    financeChangeDO.setInputFileId(fileId);
                    financeChangeRepository.save(financeChangeDO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputFileDO inputFileDO = new InputFileDO();
            inputFileDO.setId(fileId);
            inputFileDO.setStatus(InputFileStatusEnum.SUCCESS.getValue());
            inputFileRepository.save(inputFileDO);
        } catch (Exception e) {
            InputFileDO inputFileDO = new InputFileDO();
            inputFileDO.setId(fileId);
            inputFileDO.setStatus(InputFileStatusEnum.FAILED.getValue());
            inputFileRepository.save(inputFileDO);
            log.error("meet error :{}", e.getMessage(), e);
        }
    }


    private Long saveInputFile(String fileName) {
        InputFileDO inputFileDO = new InputFileDO();
        inputFileDO.setFileName(fileName);
        inputFileDO.setGmtCreate(new Date());
        inputFileDO.setStatus(InputFileStatusEnum.INPUT.getValue());
        inputFileDO = inputFileRepository.save(inputFileDO);
        return inputFileDO.getId();
    }


    @Override
    public List<FinanceChangeDO> query(FinanceChangeQTO financeChangeQTO) {
        List<FinanceChangeDO> list = financeChangeRepository.findAllByBsSnLike("%" + financeChangeQTO.getBsSn() + "%");
        upload(list);
        return list;
    }

    /**
     * 上传并且更新
     *
     * @param list
     */
    @Async
    public void upload(List<FinanceChangeDO> list) {
        File file = new File("/tmp/导出文件.xls");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet(DEFAULT_SHEET_NAME);
            HSSFRow titleRow = sheet.createRow(0);
            for (int i = 1; i <= list.size(); i++) {
                HSSFRow row = sheet.createRow(i);
                FinanceChangeDO financeChangeDO = list.get(i - 1);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(new DateTime(financeChangeDO.getTime()).toString("yyyy-MM-dd HH:mm:ss"));

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(financeChangeDO.getTradeSn());

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(financeChangeDO.getFinanceSn());

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(financeChangeDO.getFinanceSn());

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(financeChangeDO.getName());

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(FinanceChangeBsTypeEnum.getEnumByValue(financeChangeDO.getBsType()).getDesc());

                Cell cell6 = row.createCell(6);
                cell6.setCellValue(FinanceChangeTypeEnum.getEnumByValue(financeChangeDO.getType()).getDesc());

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(FinanceHelper.changeFinance(financeChangeDO.getCount()));

                Cell cell8 = row.createCell(8);
                cell8.setCellValue(FinanceHelper.changeFinance(financeChangeDO.getLeftCount()));


                Cell cell9 = row.createCell(9);
                cell9.setCellValue(financeChangeDO.getApplyName());

                Cell cell10 = row.createCell(10);
                cell10.setCellValue(financeChangeDO.getRemark());

                Cell cell11 = row.createCell(11);
                cell11.setCellValue(financeChangeDO.getBsSn());
            }
            wb.write(file);
            wb.close();

        } catch (Exception e) {
            log.error("message :{}", e.getMessage(), e);
        }

        String fileName = "open_file" + StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0") + "0"
                + AliyunOssManager.genRandomNumber(3);
        String excelUrl = aliyunOssManager.uploadFile(fileName, file);
        TaskDO taskDO = new TaskDO();
        taskDO.setTaskUrl("https://wdzg-file-test.oss-cn-hangzhou.aliyuncs.com/" + fileName);
        taskDO.setGmtCreate(new Date());
        taskRepository.save(taskDO);
    }

    public static void main(String[] args) {
        System.out.println("`交易".replace("`", ""));
        System.out.println(Long.valueOf("0.53".replace(".", "")));
    }

}
