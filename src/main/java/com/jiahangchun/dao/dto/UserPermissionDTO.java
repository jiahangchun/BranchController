package com.jiahangchun.dao.dto;

import lombok.Data;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/7 上午11:54
 **/
@Data
public class UserPermissionDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 权限名称
     */
    private String permission;
}
