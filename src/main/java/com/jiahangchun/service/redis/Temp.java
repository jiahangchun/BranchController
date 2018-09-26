package com.jiahangchun.service.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/9/25 下午1:50
 **/
public class Temp {

    public static void main(String[] args) throws Exception {
        Redisson redisson = (Redisson) Redisson.create();
        RLock lock = redisson.getLock("haogrgr");
        lock.lock();
        try {
            System.out.println("hagogrgr");
        }
        finally {
            lock.unlock();
        }
        redisson.shutdown();
    }

}
