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

    //获取打印配置
    private String printer;

    //发送打印任务
    private Task task;

    /**
     * 发送打印任务
     */
    @Data
    public class Task{
        /**
         * 打印机任务ID，每个打印任务会分配不同的且唯一的ID
         */
        private String taskID;

        /**
         * 打印通知类型:“render”, “print”
         *   [“render”] : 仅渲染响应 notify
         *   [“print”] : 仅出纸响应 notify
         *   “render”, “print” : 渲染完成会响应 notify && 出纸完成后会响应 notify
         *   [] : 不允许
         *   注:如果notifyType没有指定，默认为[“render”, “print”]
         */
        private List<String> notifyType;

        /**
         * 是否预览.true为预览,false为打印
         */
        private Boolean preview;

        /**
         * 打印机名，如果为空，会使用默认打印机
         */
        private String printer;

        /**
         * 属性取值“pdf” or “image” 预览模式，是以pdf还是image方式预览，二选一，此属性不是必选，默认以pdf预览。
         */
        private String previewType="pdf";

        /**
         * task 起始 document 序号
         */
        private Integer firstDocumentNumber;

        /**
         * task document 总数
         */
        private Integer totalDocumentCount;


        private List<Document> documents;
    }

    @Data
    public class Document{

        public String documentID;

        public List<ContentData> contents;
    }

    @Data
    public class ContentData{

        private Content data;

        private String signature;

        private String templateURL;
    }


    @Data
    public class Content{
        private Recipient recipient;

    }

    @Data
    public class Recipient{
        private String mobile;

        private String name;

        private String phone;


    }

    @Data
    public class Address{
        private String city;

        private String detail;

        private String phone;


    }

}
