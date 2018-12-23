package com.jiahangchun.DO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午5:41
 **/
@Data
@Entity(name="task")
public class TaskDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * oss
     */
    @Column
    private String taskUrl;

    /**
     * 创建时间
     */
    @Column
    private Date gmtCreate;


}
