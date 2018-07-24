package com.jiahangchun.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/23 下午7:01
 **/
@RestController
public class SampleController {

    @RequestMapping("/")
    public String index() {
        return "Hello, World";
    }

    @RequestMapping("/2")
    public String index2() {
        return "Hello, World";
    }

}
