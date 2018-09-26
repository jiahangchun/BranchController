package com.jiahangchun.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/25 上午9:23
 **/
@RestController
public class MyController {

    @RequestMapping("/test")
    public String test() throws Exception {
        throw new Exception("exception");
    }
}
