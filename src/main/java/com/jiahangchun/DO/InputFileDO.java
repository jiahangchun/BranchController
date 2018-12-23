package com.jiahangchun.DO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/23 下午1:18
 **/
@Data
@Entity(name = "input_file")
public class InputFileDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 导入文件的名称
     */
    @Column
    private String fileName;


    /**
     * 导入的时间
     */
    @Column
    private Date gmtCreate;

    /**
     * 导出状态
     */
    @Column
    private Integer status;
}
