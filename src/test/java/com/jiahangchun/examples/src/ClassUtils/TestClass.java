package com.jiahangchun.examples.src.ClassUtils;

import org.junit.Test;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/7/26 上午11:09
 **/
public class TestClass {
    static {
        System.out.println("TestClass.static");
    }

    public TestClass(){
        System.out.println("TestClass.TestClass()");
    }

    public void a(){
        System.out.println("TestClass.a()");
    }
}
