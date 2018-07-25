package com.jiahangchun.webcollection;

import lombok.Data;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/1
 * @Location: BranchController com.jiahangchun.webcollection
 */
public enum UrlEnum {

    NOVEL_BIQUGEXSW("http://www.27270.com/tag/1133.html",4L,1L,1L),
    ;



    /**
     * 目录列表的URL
     */
    private String url;


    /**
     *4.小说
     */
    private Long type;

    /**
     *
     * //2:http://www.biqugexsw.com/paihangbang/ 笔趣阁小说
     */
    private Long sourceType;

    /**
     * 类目 等以后又需要再改
     */
    private Long categoryId;


    private UrlEnum(String url,Long type,Long sourceTypeid,Long categoryId){
        this.url=url;
        this.type=type;
        this.categoryId=categoryId;
        this.sourceType=sourceTypeid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getSourceType() {
        return sourceType;
    }

    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
}
