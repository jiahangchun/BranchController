package com.jiahangchun.jdk;

public class ThreadLocalDemo {

    private static ThreadLocal<Integer> initVal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void modify() {
        initVal.set( 10);
        initVal.set(20);
        initVal.get();
        System.out.println("111   "+initVal.get());
        initVal.set(initVal.get() + 20);
        System.out.println(initVal.get());
    }


    public static void main(String[] args) {
        new Thread("Thread#1") {
            @Override
            public void run() {
                modify();
            }
        }.start();
        new Thread("Thread#2") {
            @Override
            public void run() {
                modify();
            }
        }.start();
    }
}