package com.jiahangchun.constant;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.constant
 */
public enum  ResourceEnum {

    //笔趣阁的小说
    NOVEL_BIQUGE(1L);

    private Long value;

    ResourceEnum(Long value){
        this.value=value;
    }

    public Long getValue(){
        return value;
    }

}
