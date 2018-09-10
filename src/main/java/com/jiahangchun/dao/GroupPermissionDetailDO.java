package com.jiahangchun.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:39
 **/
@Entity
@Table(name = "group_permission_detail")
@Data
public class GroupPermissionDetailDO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 组的id
     */
    private Long groupId;

    /**
     * 权限id
     */
    private Long permissionId;

}
