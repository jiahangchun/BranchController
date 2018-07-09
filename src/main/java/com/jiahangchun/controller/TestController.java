package com.jiahangchun.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/9 下午11:08
 **/
@RestController
public class TestController {


    @RequestMapping("test/1")
    public String test1(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //主体名，即登录用户名
        System.out.println(name);
        //default username: anonymousUser
        return "test1";
    }
}
