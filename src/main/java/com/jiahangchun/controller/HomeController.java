package com.jiahangchun.controller;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/24 下午8:10
 **/
@RestController
public class HomeController {


    @RequestMapping("/")
    public void home(Device device) {
        if (device.isMobile()) {
            System.out.println("Hello mobile user!");
        } else if (device.isTablet()) {
            System.out.println("Hello tablet user!");
        } else if(device.isNormal()){
            System.out.println("Hello normal user!");
        }else{
            System.out.println("no site preference");
        }
    }

}