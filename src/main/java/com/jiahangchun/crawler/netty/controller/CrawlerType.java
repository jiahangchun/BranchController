package com.jiahangchun.crawler.netty.controller;
/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/27 下午5:20
 **/
public enum  CrawlerType {

    /**
     * 笔趣阁的首页计算
     */
    BI_QU_GE("笔趣阁的首页",1,"BQGHomePageProcess"),
    BI_QU_GE_JSON("笔趣阁的json查探",2,"BQGHomePageProcess"),
    BI_QU_GE_BOOK("笔趣阁的文本",3,"");

    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 处理器
     */
    private String processName;

    CrawlerType(String name,Integer type,String processName){
        this.name=name;
        this.type=type;
        this.processName=processName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
