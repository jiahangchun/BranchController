package com.jiahangchun.DO;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/9 上午11:41
 **/
@Data
@Entity(name="user")
public class UserDO {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;
}
