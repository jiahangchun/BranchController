package com.jiahangchun.constant;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.constant
 */
public enum  ExceptionInfoTypeEnum {

    //笔趣阁的小说 获取内容失败
    NOVEL_BIQUGE_CONTENT_EXCEPTION(1L),

    //笔趣阁的小说 获取目录失败
    NOVEL_BIQUGE_CATEGORY_EXCEPTION(2L);

    private Long value;

    ExceptionInfoTypeEnum(Long value){
        this.value=value;
    }

    public Long getValue(){
        return value;
    }

}
