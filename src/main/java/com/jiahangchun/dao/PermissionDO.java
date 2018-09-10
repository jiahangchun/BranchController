package com.jiahangchun.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:33
 **/
@Entity
@Table(name = "permission")
@Data
public class PermissionDO {


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 申请的权限名称
     */
    private String permission;
}
