package com.jiahangchun.controller;

import com.google.common.base.Preconditions;
import com.jiahangchun.dao.*;
import com.jiahangchun.dao.respository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chunchun
 * @descritpion 设置 & 添加相关基础权限
 * @date Created at 2018/9/10 上午9:17
 **/
@RestController
@Api(value = "权限管理", description = "添加不同的权限")
public class AddController {
    @Resource
    private UsersRepository usersRepository;
    @Resource
    private UserGroupDetailRepository userGroupDetailRepository;
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private GroupsRepository groupsRepository;
    @Resource
    private GroupPermissionDetailRepository groupPermissionDetailRepository;


    /**
     * @param usersDO
     * @return
     */
    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "user/add", name = "添加用户", method = RequestMethod.POST)
    public String addUsers(@RequestBody UsersDO usersDO) {
        Preconditions.checkArgument(usersDO.getName() != null, "name 不能为空");
        Preconditions.checkArgument(usersDO.getPassword() != null, "password 不能为空");
        usersDO.setId(null);
        usersRepository.save(usersDO);
        return "add users";
    }

    /**
     * @param groupsDO
     * @return
     */
    @ApiOperation(value = "添加组")
    @RequestMapping(value = "group/add", name = "添加组", method = RequestMethod.POST)
    public String addGroup(@RequestBody GroupsDO groupsDO) {
        Preconditions.checkArgument(groupsDO.getName() != null, "name 不能为空");
        groupsDO.setId(null);
        groupsRepository.save(groupsDO);
        return "add group";
    }


    /**
     * @param userGroupDetailDO
     * @return
     */
    @ApiOperation(value = "添加组和用户关系")
    @RequestMapping(value = "user_group/add", name = "添加组和用户关系", method = RequestMethod.POST)
    public String addUserGroup(@RequestBody UserGroupDetailDO userGroupDetailDO) {
        Preconditions.checkArgument(userGroupDetailDO.getGroupId() != null, "groupId 不能为空");
        Preconditions.checkArgument(userGroupDetailDO.getUserId() != null, "userId 不能为空");
        userGroupDetailDO.setId(null);
        userGroupDetailRepository.save(userGroupDetailDO);
        return "add user_group";
    }

    /**
     * @param permissionDO
     * @return
     */
    @ApiOperation(value = "添加权限")
    @RequestMapping(value = "permission/add", name = "添加权限", method = RequestMethod.POST)
    public String addPermission(@RequestBody PermissionDO permissionDO) {
        Preconditions.checkArgument(permissionDO.getName() != null, "name 不能为空");
        Preconditions.checkArgument(permissionDO.getPermission() != null, "permission 不能为空");
        permissionDO.setId(null);
        permissionRepository.save(permissionDO);
        return "add permission";
    }

    /**
     * @param groupPermissionDetailDO
     * @return
     */
    @ApiOperation(value = "添加组和权限的关系")
    @RequestMapping(value = "group_permission/add", name = "添加组和权限的关系", method = RequestMethod.POST)
    public String addGroupPermission(@RequestBody GroupPermissionDetailDO groupPermissionDetailDO) {
        Preconditions.checkArgument(groupPermissionDetailDO.getPermissionId() != null, "permissionId 不能为空");
        Preconditions.checkArgument(groupPermissionDetailDO.getGroupId() != null, "groupId 不能为空");
        groupPermissionDetailDO.setId(null);
        groupPermissionDetailRepository.save(groupPermissionDetailDO);
        return "add user_permission";
    }
}
