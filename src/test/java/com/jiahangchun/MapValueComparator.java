package com.jiahangchun;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/26 下午3:48
 **/
public class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

        return me2.getValue().compareTo(me1.getValue());
    }

    @Override
    public Comparator<Map.Entry<String, Integer>> reversed() {
        return null;
    }

    @Override
    public Comparator<Map.Entry<String, Integer>> thenComparing(Comparator<? super Map.Entry<String, Integer>> other) {
        return null;
    }

    @Override
    public <U> Comparator<Map.Entry<String, Integer>> thenComparing(Function<? super Map.Entry<String, Integer>, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Map.Entry<String, Integer>> thenComparing(Function<? super Map.Entry<String, Integer>, ? extends U> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Map.Entry<String, Integer>> thenComparingInt(ToIntFunction<? super Map.Entry<String, Integer>> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Map.Entry<String, Integer>> thenComparingLong(ToLongFunction<? super Map.Entry<String, Integer>> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Map.Entry<String, Integer>> thenComparingDouble(ToDoubleFunction<? super Map.Entry<String, Integer>> keyExtractor) {
        return null;
    }


}