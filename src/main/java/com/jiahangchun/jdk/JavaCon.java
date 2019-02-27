package com.jiahangchun.jdk;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/2/26 下午1:53
 **/
public class JavaCon {
    private Lock lock = new ReentrantLock();

    public void testMethod() throws InterruptedException {
        lock.lock();
//        lock.lock();
        Long value=(long) (Math.random()*1000);
            System.out.println("ThreadName=" + Thread.currentThread().getName()
                    + "   "+value);
            Thread.sleep(value);
        lock.unlock();
    }



    public static void main(String[] args) {
        JavaCon con=new JavaCon();
        for(int i=0;i<1000000;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        con.testMethod();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
