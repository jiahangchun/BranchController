package com.jiahangchun.controller;

import com.jiahangchun.App;
import com.jiahangchun.dao.*;
import com.jiahangchun.tool.JsonUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/10 上午10:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
//@Transactional(rollbackForClassName = "UserController")
public class UserController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional(rollbackForClassName = "UserController")
    public void testUserAdd() throws Exception {
        UsersDO usersDO = new UsersDO();
        usersDO.setName("name");
        usersDO.setPassword("password");

        this.mockMvc.perform(post("/user/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(usersDO))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add users")));
    }


    @Test
    public void testGroupAdd() throws Exception {
        GroupsDO groupsDO = new GroupsDO();
        groupsDO.setName("group_name");

        this.mockMvc.perform(post("/group/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(groupsDO))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add group")));
    }

    @Test
    public void testAddUserGroup() throws Exception {
        UserGroupDetailDO userGroupDetailDO=new UserGroupDetailDO();
        userGroupDetailDO.setUserId(10L);
        userGroupDetailDO.setGroupId(1L);

        this.mockMvc.perform(post("/user_group/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(userGroupDetailDO))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add user_group")));
    }


    @Test
    public void testAddPermission() throws Exception {
        PermissionDO permissionDO=new PermissionDO();
//        permissionDO.setName("权限1");
//        permissionDO.setPermission("权限1");
        permissionDO.setName("/user/1");
        permissionDO.setPermission("/user/1");

        this.mockMvc.perform(post("/permission/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(permissionDO))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add permission")));
    }

    @Test
    public void testAddGroupsPermission() throws Exception {
        GroupPermissionDetailDO groupPermissionDetailDO=new GroupPermissionDetailDO();
        groupPermissionDetailDO.setGroupId(1L);
        groupPermissionDetailDO.setPermissionId(2L);

        this.mockMvc.perform(post("/group_permission/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(groupPermissionDetailDO))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add user_permission")));
    }

    @Test
    public void test(){
        UserGroupDetailDO userGroupDetailDO=new UserGroupDetailDO();
        userGroupDetailDO.setUserId(10L);
        userGroupDetailDO.setGroupId(1L);
        System.out.println(JsonUtil.toJson(userGroupDetailDO));
    }
}
