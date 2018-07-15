package com.jiahangchun.util;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.util
 */
public class JsonUtil {

    public static Gson gson;

    static {
        GsonBuilder gb=new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gb.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double src, Type type, JsonSerializationContext jsonSerializationContext) {
                if(src == src.longValue()){
                    return new JsonPrimitive(src.longValue());
                }
                return  new JsonPrimitive(src);
            }
        });
        gson=gb.create();
    }

    public static <T> T parseJson(String jsonStr,Class<T> tClass){
        return gson.fromJson(jsonStr,tClass);
    }

    public static String toJson(Object src){
        return gson.toJson(src);
    }
}
