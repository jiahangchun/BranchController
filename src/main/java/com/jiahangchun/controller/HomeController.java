package com.jiahangchun.controller;

import com.jiahangchun.annotation.SecurityAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/24 下午8:10
 **/
@RestController
public class HomeController {


    @Autowired
    WebApplicationContext applicationContext;

    @SecurityAnnotation(level = 1)
    @RequestMapping("/")
    public void home(Device device) {
        if (device.isMobile()) {
            System.out.println("Hello mobile user!");
        } else if (device.isTablet()) {
            System.out.println("Hello tablet user!");
        } else if (device.isNormal()) {
            System.out.println("Hello normal user!");
        } else {
            System.out.println("no site preference");
        }
    }

    @SecurityAnnotation(level = 2)
    @RequestMapping(value = "/test",
            method = {RequestMethod.POST, RequestMethod.GET},
            headers = {"a=1"},
            consumes = {"application/json"},
            produces = {"application/json"})
    public void test(Device device) {
        System.out.println("lalal");
    }

    @RequestMapping("/url")
    public String allUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo info : map.keySet()) {
            //获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                urlList.add(url);
            }
        }

        for (HandlerMethod method : map.values()) {
            RequestMapping requestMapping = method.getMethodAnnotation(RequestMapping.class);
            String[] path = requestMapping.path();
            Arrays.stream(path).forEach((String p) -> {
                System.out.println(p);
            });

            SecurityAnnotation securityAnnotation = method.getMethodAnnotation(SecurityAnnotation.class);
            if (securityAnnotation != null) {
                System.out.println(securityAnnotation.level());
            }
        }

        urlList.stream().forEach((String url) -> {
            System.out.println(url);
        });
        return "";
    }

}