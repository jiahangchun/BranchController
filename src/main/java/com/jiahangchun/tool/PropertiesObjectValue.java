package com.jiahangchun.tool;

import lombok.Data;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/11 下午2:26
 **/
@Data
public class PropertiesObjectValue {
    private Long id;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据属性名称
     */
    private String dataName;

    /**
     * 数据类型
     */
    private String dataType;

    private Boolean isNotValue;

    private Boolean isUnique;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 评论
     */
    private String commnets;
}
