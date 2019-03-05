package com.jiahangchun.jdk;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;
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
//        lock.lock();
//        lock.lock();
        Long value=(long) (Math.random()*10000);
            System.out.println("ThreadName=" + Thread.currentThread().getName()
                    + "   "+value);
            Thread.sleep(value);
//        lock.unlock();
    }

    public void dead() throws InterruptedException {
        System.out.println("start thread ThreadName : "+Thread.currentThread().getName());
        while (true){
            Thread.sleep(1000);
        }
    }



    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue=new LinkedBlockingQueue<>(10);
//        queue.add("aaaaa");
//        queue.offer("ddddd");

        String poll=queue.poll();
        for(int i=0;i<100;i++){
//            queue.add("sss");
            String take=queue.take();
        }



        JavaCon con=new JavaCon();
        BlockingQueue<Runnable> blockingQueue=new LinkedBlockingQueue<Runnable>(10);
        RejectedExecutionHandler rejectedExecutionHandler=new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("reject name "+r.getClass());
            }
        };
        ThreadPoolExecutor threadPoolExecutor= new ThreadPoolExecutor(4,10,1000,TimeUnit.SECONDS,blockingQueue,new ThreadPoolExecutor.DiscardPolicy());
        for(int i=0;i<1000000;i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        con.testMethod();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
