package com.jiahangchun.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/6/22 下午3:24
 **/
public class JsonUtil {


    /**
     * 使用Gson生成json字符串
     * @param src
     * @return
     */
    public static String toJson(Object src){
        return JSON.toJSONString(src);
    }
}
