package com.jiahangchun.webcollection;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/1
 * @Location: BranchController com.jiahangchun.webcollection
 */
public enum UrlEnum {

    FIRST_RENTI("http://www.27270.com/tag/1133.html",1L,1L);
    ;

    /**
     * 目录列表的URL
     */
    private String url;

    /**
     * //1:人体艺术，2：邪恶漫画；3：动态图片；4.小说
     */
    private Long type;

    /**
     * //1:http://www.27270.com/tag/1133.html 人体艺术，半裸
     */
    private Long sourceType;

    private UrlEnum(String url,Long type,Long sourceType){
        this.url=url;
        this.type=type;
        this.setSourceType(sourceType);
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

}
