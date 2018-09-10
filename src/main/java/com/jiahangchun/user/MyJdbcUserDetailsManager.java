package com.jiahangchun.user;

import com.jiahangchun.dao.*;
import com.jiahangchun.dao.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/30 上午10:49
 **/
@Service
public class MyJdbcUserDetailsManager implements UserDetailsManager, GroupManager {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserGroupDetailRepository userGroupDetailRepository;
    @Autowired
    private GroupPermissionDetailRepository groupPermissionDetailRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersDO usersDO = usersRepository.findByNameEquals(username);
        if (null == usersDO || usersDO.getId() <= 0) {
            throw new UsernameNotFoundException("找不到对应的用户");
        }
        //查询相关的组
        List<UserGroupDetailDO> userGroupDetailDOS = userGroupDetailRepository.findAllByUserIdEquals(usersDO.getId());
        if (null == userGroupDetailDOS || userGroupDetailDOS.size() <= 0) {
            throw new RuntimeException("没找到相关的组");
        }
        //查找相关的权限关系
        List<Long> groupIdList = userGroupDetailDOS.stream().map(UserGroupDetailDO::getGroupId).collect(Collectors.toList());
        List<GroupPermissionDetailDO> groupPermissionDetailDOS = groupPermissionDetailRepository.findAllByGroupIdIn(groupIdList);
        List<Long> permissionIdList = groupPermissionDetailDOS.stream().map(GroupPermissionDetailDO::getPermissionId).collect(Collectors.toList());
        List<PermissionDO> permissionDOS = permissionRepository.findAllByIdIn(permissionIdList);
        List<GrantedAuthority> permissions = permissionDOS.stream().map(permissionDO -> {
            return new SimpleGrantedAuthority(permissionDO.getPermission());
        }).collect(Collectors.toList());

        //组合成相应的user对象
        return new User(usersDO.getName(), usersDO.getPassword(), true, true, true, true, permissions);
    }

    @Override
    public List<String> findAllGroups() {
        List<GroupsDO> groupsDOS = groupsRepository.findAll();
        List<String> groupNames = groupsDOS.stream().map(groupsDO -> {
            return groupsDO.getName();
        }).collect(Collectors.toList());
        return groupNames;
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        List<UsersDO> usersDOS = userGroupDetailRepository.findAllByGroupNameLike(groupName);
        List<String> usersName = usersDOS.stream().map(UsersDO::getName).collect(Collectors.toList());
        return usersName;
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(groupName);


        authorities.stream().map(grantedAuthority -> {
            //获取具体的权限名称
            String authority = grantedAuthority.getAuthority();

            //获取权限的id
            Long permissionDOId = 0L;
            PermissionDO queryPermissionDO = permissionRepository.findByPermissionEquals(authority);
            if (null != queryPermissionDO) {
                PermissionDO permissionDO = new PermissionDO();
                permissionDO.setName(authority);
                permissionDO.setPermission(authority);
                permissionRepository.save(permissionDO);
                permissionDOId = permissionDO.getId();
            } else {
                permissionDOId = queryPermissionDO.getId();
            }

            //添加对应的表
            GroupPermissionDetailDO groupPermissionDetailDO = new GroupPermissionDetailDO();
            groupPermissionDetailDO.setGroupId(groupsDO.getId());
            groupPermissionDetailDO.setPermissionId(permissionDOId);
            return groupPermissionDetailDO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteGroup(String groupName) {
        groupsRepository.deleteByNameEquals(groupName);
        //TODO 删除这个组的权限
        //TODO 需要设置cache么？
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(oldName);
        if (null != groupsDO || groupsDO.getId() != null) {
            groupsDO.setName(newName);
            groupsRepository.save(groupsDO);
        }
    }

    @Override
    public void addUserToGroup(String username, String group) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(group);
        Long groupId = groupsDO.getId();

        UsersDO usersDO = usersRepository.findByNameEquals(username);
        Long userId = usersDO.getId();

        UserGroupDetailDO userGroupDetailDO = new UserGroupDetailDO();
        userGroupDetailDO.setGroupId(groupId);
        userGroupDetailDO.setUserId(userId);
        userGroupDetailRepository.save(userGroupDetailDO);
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(groupName);
        Long groupId = groupsDO.getId();

        UsersDO usersDO = usersRepository.findByNameEquals(username);
        Long userId = usersDO.getId();

        UserGroupDetailDO userGroupDetailDO = userGroupDetailRepository.findByGroupIdEqualsAndUserIdEquals(groupId, userId);
        if (null != userGroupDetailDO) {
            userGroupDetailRepository.delete(userGroupDetailDO.getId());
        }
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(groupName);
        Long groupId = groupsDO.getId();

        List<GroupPermissionDetailDO> groupPermissionDetailDOS = groupPermissionDetailRepository.findAllByGroupIdEquals(groupId);

        groupPermissionDetailDOS.stream().map(groupPermissionDetailDO -> {
            return new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    Long permissionId = groupPermissionDetailDO.getPermissionId();
                    PermissionDO permissionDO = permissionRepository.findOne(permissionId);
                    return permissionDO.getPermission();
                }
            };
        }).collect(Collectors.toList());

        return null;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(groupName);
        Long groupId = groupsDO.getId();
        String permission = authority.getAuthority();
        PermissionDO permissionDO = getPermissionId(permission);
        GroupPermissionDetailDO groupPermissionDetailDO = new GroupPermissionDetailDO();
        groupPermissionDetailDO.setGroupId(groupId);
        groupPermissionDetailDO.setPermissionId(permissionDO.getId());
        groupPermissionDetailRepository.save(groupPermissionDetailDO);
    }

    /**
     * 获取对应的权限id , 如果没有就创建一个新的权限
     *
     * @param
     * @return
     */
    private PermissionDO getPermissionId(String permission) {
        PermissionDO permissionDO = permissionRepository.findByPermissionEquals(permission);
        if (null == permissionDO || permissionDO.getId() <= 0) {
            PermissionDO permissionDO1 = new PermissionDO();
            permissionDO1.setPermission(permission);
            permissionDO1.setName(permission);
            permissionRepository.save(permissionDO1);
            permissionDO.setId(permissionDO1.getId());
        }
        return permissionDO;
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        GroupsDO groupsDO = groupsRepository.findByNameEquals(groupName);
        Long groupId = groupsDO.getId();

        String permission = authority.getAuthority();
        PermissionDO permissionDO = permissionRepository.findByPermissionEquals(permission);

        groupPermissionDetailRepository.deleteAllByGroupIdEqualsAndPermissionIdEquals(groupId, permissionDO.getId());
    }

    @Override
    public void createUser(UserDetails user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        UsersDO usersDO = new UsersDO();
        usersDO.setName(userName);
        usersDO.setPassword(password);
        usersRepository.save(usersDO);
    }

    @Override
    public void updateUser(UserDetails user) {
        UsersDO usersDO = usersRepository.findByNameEquals(user.getUsername());
        if (null != usersDO && usersDO.getId() > 0) {
            usersDO.setPassword(user.getPassword());
            usersRepository.save(usersDO);
        }
    }

    @Override
    public void deleteUser(String username) {
        //TODO
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //TODO
    }

    @Override
    public boolean userExists(String username) {
        UsersDO usersDO = usersRepository.findByNameEquals(username);
        if (null != usersDO && usersDO.getId() > 0) {
            return true;
        }
        return false;
    }
}
