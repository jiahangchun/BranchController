package com.jiahangchun.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.manager
 */
@Service
@Log4j2
public class ThreadPoolManager {
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolManager(){
        threadPoolExecutor=new ThreadPoolExecutor(32,64,10, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(500));
    }

    public void execute(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolManager threadPoolManager=new ThreadPoolManager();
        threadPoolManager.execute(() -> {
            try {
                System.out.println("aaa");
            }catch (Exception e){
                log.error(e.getMessage());
            }
        });
        System.out.println(threadPoolManager.threadPoolExecutor.getActiveCount());
        System.out.println("bbb");
        Thread.sleep(1000);
        System.out.println(threadPoolManager.threadPoolExecutor.getActiveCount());
    }

}

