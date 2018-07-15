package com.jiahangchun.constant;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.constant
 */
public enum  CategotyEnum {
    //小说
    NOVEL(1L);

    private Long value;

    CategotyEnum(Long value){
        this.value=value;
    }

    public Long getValue(){
        return value;
    }
}
