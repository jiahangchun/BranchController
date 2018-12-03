package com.jiahangchun;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/28 下午3:27
 **/
public class LRU {

    public static void main(String[] args) {
        HashMap<Long,String> map=new LinkedHashMap<Long,String>(100, (float) 0.75,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, String> eldest) {
                return super.removeEldestEntry(eldest);
            }
        };
        map.put(1L,"sss");
        map.get(1L);
    }


}
