package com.jiahangchun.controller.aop;

import com.jiahangchun.application.AppContext;
import com.jiahangchun.application.TestEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/9 下午5:01
 **/
@RestController
public class AopController {

    @Resource
    private AppContext appContext;

    @RequestMapping("/first")
    public Object first() {
        return "first controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

    @RequestMapping("/testEvent")
    public Object testEvent() {
        TestEvent testEvent=new TestEvent("ddddd","ddddd");
        appContext.getApplicationContext().publishEvent(testEvent);
        return "vvv";
    }
}
