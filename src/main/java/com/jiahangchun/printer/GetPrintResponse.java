package com.jiahangchun.printer;

import lombok.Data;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/6 上午11:07
 **/
@Data
public class GetPrintResponse extends Response{

    private String taskID;

    private String previewURL;

    private List<String> previewImage;
}
