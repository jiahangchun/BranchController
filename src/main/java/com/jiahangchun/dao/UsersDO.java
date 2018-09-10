package com.jiahangchun.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/1 下午6:23
 **/
@Entity
@Table(name = "users")
@Data
public class UsersDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String password;
}
