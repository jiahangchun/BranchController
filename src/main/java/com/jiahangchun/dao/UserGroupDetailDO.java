package com.jiahangchun.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/6 下午11:37
 **/
@Entity
@Table(name = "user_group_detail")
@Data
public class UserGroupDetailDO {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;


    /**
     * 组的id
     */
    private Long groupId;

}
