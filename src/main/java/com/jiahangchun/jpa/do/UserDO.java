package com.jiahangchun.DO;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/9 上午11:41
 **/
@Data
@Entity(name="user")
public class UserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 手机号
     */
    @Column
    private String mobile;

    /**
     * 密码
     */
    @Column
    private String password;
}
