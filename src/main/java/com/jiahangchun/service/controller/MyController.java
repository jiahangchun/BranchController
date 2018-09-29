package com.jiahangchun.service.controller;

import com.jiahangchun.service.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/25 上午9:23
 **/
@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping("/test")
    public String test() throws Exception {
        myService.a();
        throw new Exception("exception");
    }
}
