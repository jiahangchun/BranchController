package com.jiahangchun.controller.test;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {

    private AtomicInteger countA=new AtomicInteger(0);
    private AtomicInteger countB=new AtomicInteger(0);

    @RequestMapping(path = "/list/cityId/{cityId}", method = RequestMethod.GET)
    @ResponseBody
    public String getJsonByCityIdPathVariable(@PathVariable Integer cityId){
        System.out.println("a"+countA.incrementAndGet() );
        return "a";
    }

    @RequestMapping(path = "/list/cityId", method = RequestMethod.GET)
    @ResponseBody
    public String getJsonByCityIdRequestParam(@RequestParam Integer cityId){
        System.out.println("b"+countB.incrementAndGet() );
        return "b";
    }

}
