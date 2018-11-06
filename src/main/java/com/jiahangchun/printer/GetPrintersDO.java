package com.jiahangchun.printer;

import lombok.Data;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/5 下午3:54
 **/
@Data
public class GetPrintersDO {

    private String cmd;

    private String requestID;

    private String version;

    private String defaultPrinter;

    private List<Detail> printers;

    @Data
    public class Detail{

        private String name;
    }
}
