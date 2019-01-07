package com.jiahangchun.jdk;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import sun.jvm.hotspot.utilities.Assert;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/5 下午3:42
 **/
public class BigData {
    public static void main(String[] args) {
        BloomFilter<Integer> filter=BloomFilter.create(Funnels.integerFunnel(),100000,0.01);
        for(int i=0;i<1000000;i++){
            filter.put(i);
        }
        System.out.println(filter.mightContain(222));
    }
}
