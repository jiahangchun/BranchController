package com.jiahangchun.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/10 上午8:53
 **/
@RestController
public class UserController {
    @RequestMapping("user/1")
    public String test1(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //主体名，即登录用户名
        System.out.println(name);
        //default username: anonymousUser
        return "user1";
    }
}
