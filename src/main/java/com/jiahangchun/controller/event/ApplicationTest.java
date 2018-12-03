package com.jiahangchun.controller.event;

import com.jiahangchun.event.TestEvent;
import com.jiahangchun.util.GetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/23 上午10:55
 **/
@RestController
public class ApplicationTest {

    @RequestMapping("/event")
    public String testEvent(){
        long a=10_000_000;
        GetConfig.getApplicationContext().publishEvent(new TestEvent("abc"));
        return "";
    }


    public static void main(String[] args) {
        String a=null;
        if(a==null){//not equal
            System.out.println("a");
        }
        System.out.println("b");
    }
}
