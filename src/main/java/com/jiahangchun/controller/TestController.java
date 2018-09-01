package com.jiahangchun.controller;

import com.jiahangchun.config.MyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/29 下午2:49
 **/
@RestController
public class TestController {

    /**
     * 测试接口
     * @return
     */
    @RequestMapping(value = "user/1", name = "测试用户1接口权限", method = RequestMethod.GET)
    public String test1() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //主体名，即登录用户名
        System.out.println(name);
        return "user1";
    }
}
