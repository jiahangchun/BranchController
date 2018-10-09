package com.jiahangchun.controller;

import com.google.common.base.Preconditions;
import com.jiahangchun.dao.GroupsDO;
import com.jiahangchun.dao.UsersDO;
import com.jiahangchun.manager.UsersManager;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/29 下午2:49
 **/
@RestController
@Api(value = "本地测试", description = "不用管的")
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private UsersManager usersManager;

    /**
     * 添加用户测试
     *
     * @return
     */
    @RequestMapping(value = "user/save", name = "添加用户", method = RequestMethod.GET)
    public String addUsers() {
        usersManager.test();
        return "add users";
    }

    @RequestMapping(value = "test/access", name = "测试", method = RequestMethod.GET)
    public String testAccess() {
//        usersManager.test();
        return "add users";
    }

    /**
     * @return
     */
    @ApiOperation(value = "添加测试swagger")
    @RequestMapping(value = "swagger/test", name = "添加测试swagger", method = RequestMethod.POST)
    public String testSwagger(@ApiParam(name="参数id",required = true,example = "123",defaultValue = "1234",value = "12345") @RequestParam(name="id号")Long id,
                           @ApiParam(name="参数名称",required = true,example = "name1",defaultValue = "name2",value = "name3") @RequestParam(name="名称")String name) {
        return "testSwagger";
    }

    /**
     * 测试接口
     *
     * @return
     */
    @RequestMapping(value = "user/1", name = "测试用户1接口权限", method = RequestMethod.GET)
    public String test1() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //主体名，即登录用户名
        System.out.println(name);
        return "user1";
    }

    /**
     * 测试接口
     *
     * @return
     */
    @RequestMapping(value = "users/signUp", name = "注册", method = RequestMethod.POST)
    public void signUp(@RequestBody UsersDO users) {
        System.out.println("signUp");
    }

}
