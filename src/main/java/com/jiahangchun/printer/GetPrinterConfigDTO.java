package com.jiahangchun.printer;

import lombok.Data;

/**
 * @author chunchun
 * @descritpion
 * 参数含义说明 https://open.taobao.com/doc.htm?docId=107014&docType=1
 * @date Created at 2018/11/6 上午10:19
 **/
@Data
public class GetPrinterConfigDTO {

    private String cmd;

    private String requestID;

    /**
     * success/failed
     * 标示命令成功或失败，取值“success”或者“failed”
     */
    private String status;

    private String msg;

    private Printer printer;

    @Data
    public class Printer{

        public String name;

        public Boolean needTopLogo;

        public Boolean needBottomLogo;

        public Integer horizontalOffset;

        public Integer verticalOffset;

        public Boolean forceNoPageMargins;

        public Boolean autoPageSize;

        public Integer orientation;

        public Boolean autoOrientation;

        public PaperSize paperSize;
    }

    @Data
    public class PaperSize{

        public Integer width;

        public Integer height;
    }

}
