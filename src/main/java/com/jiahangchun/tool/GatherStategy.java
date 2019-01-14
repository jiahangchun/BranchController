package com.jiahangchun.tool;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/11 下午2:22
 **/
public interface GatherStategy {

    /**
     * 根据不同数据库获取tableNames
     * @return
     */
    public String getTableNames();

    /**
     * 获取参数
     * @return
     */
    public PropertiesObjectValue getPropertiesObjectValue();

}
