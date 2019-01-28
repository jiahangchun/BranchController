package com.jiahangchun.crawler.netty.demo.discard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/28 下午4:21
 **/
public class URLTest {

    private static String BASE = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789-_";
    private static Integer BASE_SIZE = BASE.length();

    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue=new ConcurrentLinkedQueue<>();
    private static final Integer DEFAULT_AVALIABLE_SIZE=5000;

    public List<String> genPathPartList(Integer size) {
        List<String> list = new ArrayList<>();
        if(size>=2) {
            List<String> smallPart = genPathPartList(size - 1);
            for (String part : smallPart) {
                list.add(part);
                for (int i = 0; i < BASE_SIZE; i++) {
                    StringBuilder sb=new StringBuilder(part);
                    sb.append(BASE.charAt(i));
                    //这样下去内存会奔溃的，必须提前处理掉一部分
                    list.add(sb.toString());
                }
            }
        }else if(size==1){
            for (int i = 0; i < BASE_SIZE; i++) {
                list.add(String.valueOf(BASE.charAt(i)));
            }
        }
        return list;
    }

    /**
     * 合并 size 这里为了方便使用的是偶数
     * @param baseList
     */
    public List<String> merge(List<String> baseList,Integer size){
        if(null==baseList || baseList.size()<=0){
            return new ArrayList<>();
        }

        for(String baseIndex:baseList){

        }
        return null;
    }

    public static void main(String[] args) {
        //基础
        List<String> list=new URLTest().genPathPartList(2);
        list.forEach(System.out::println);
        System.out.println(list.size());
    }
}
