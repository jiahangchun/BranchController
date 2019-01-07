package com.jiahangchun.jdk;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/3 下午1:41
 **/
public class Test {

    public static void main(String[] args) {
        HashMap<Integer,String> map=new HashMap<>();
        map.put(1,"1111");

        ConcurrentHashMap<Integer,String> concurrentHashMap=new ConcurrentHashMap<>();
        concurrentHashMap.put(1,"1111");
        concurrentHashMap.put(2,"22222");
        concurrentHashMap.put(2,"33333");
        for(int i=0;i<100000000;i++){
            concurrentHashMap.put(i,"ddd");
        }
    }



}
