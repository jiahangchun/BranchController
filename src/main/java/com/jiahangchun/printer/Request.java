package com.jiahangchun.printer;

import lombok.Data;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/6 上午10:17
 **/
@Data
public class Request {

    private String cmd;

    private String version;

    private String requestID;

    private String printer;//获取打印配置

    private Task task;//发送打印任务

    /**
     * 发送打印任务
     */
    @Data
    public class Task{
        private String taskID;

        private Boolean preview;

        private String printer;

        private String previewType="pdf";

        private Integer firstDocumentNumber;

        private Integer totalDocumentCount;

        private List<Document> documents;
    }

    @Data
    public class Document{

    }
}
