package com.jiahangchun.printer;

import lombok.Data;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/6 上午11:10
 **/
@Data
public class GetNotifyPrintResultResponse  extends Response{

    public String printer;

    public String taskID;

    public String taskStatus;

    public List<PrintStatus> printStatus;

    @Data
    public static class PrintStatus {
        public String documentID;

        public String status;

        public String msg;

        public String detail;
    }
}
