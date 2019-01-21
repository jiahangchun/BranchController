package com.jiahangchun.fescar;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import org.springframework.core.annotation.Order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/1/16 下午5:19
 **/
public class Test {

    private ExecutorService configOperateExecutor;

    public Test() {
        configOperateExecutor = new ThreadPoolExecutor(1, 2,
                Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory("configOperate", true));
    }

    /**
     * 至于写得这么麻烦么？为了追求一点时间 #ConfigFuture
     * @param args
     */
    public static void main(String[] args) {
        Test test=new Test();
        ConfigFuture configFuture=test.new ConfigFuture();
        test.configOperateExecutor.submit(test.new ConfigOperateRunnable(configFuture));
        String returnValue= (String)configFuture.get();
    }

    class ConfigOperateRunnable implements Runnable {

        ConfigFuture configFuture=null;

        public ConfigOperateRunnable(ConfigFuture configFuture){
            this.configFuture=configFuture;
        }

        @Override
        public void run() {

        }
    }

    class ConfigFuture{
        public String get(){
            return "success";
        }
    }

}
