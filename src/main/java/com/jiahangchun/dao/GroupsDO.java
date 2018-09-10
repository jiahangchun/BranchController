package com.jiahangchun.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:36
 **/
@Entity
@Table(name = "groups")
@Data
public class GroupsDO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 组名
     */
    private String name;

}
