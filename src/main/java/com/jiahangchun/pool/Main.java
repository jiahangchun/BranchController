package com.jiahangchun.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.List;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/1 下午2:48
 **/
public class Main {



    public static void main(String[] args) throws Exception {
        setObjectPool();

        for(int i=0;i<=10;i++) {
            TestObject testObject = objectPool.borrowObject();
            testObject.add();
            TestObject testObject2 = objectPool.borrowObject();
            testObject2.add();
            objectPool.returnObject(testObject2);
            objectPool.returnObject(testObject);
        }

        objectPool.close();
    }

    private static GenericObjectPool<TestObject> objectPool = null;

    public static void setObjectPool(){
        //工厂
        TestPoolFactory factory = new TestPoolFactory();
        //资源池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //设置最大实例总数
        poolConfig.setMaxTotal(2);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        poolConfig.setMinIdle(500);
        poolConfig.setMaxIdle(1000);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        poolConfig.setMaxWaitMillis(1000*10);
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
        poolConfig.setTestOnBorrow(true);
        // 在还会给pool时，是否提前进行validate操作
        poolConfig.setTestOnReturn(true);
        //如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        poolConfig.setTestWhileIdle(true);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        poolConfig.setMinEvictableIdleTimeMillis(6000);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);

        //创建资源池
        objectPool = new GenericObjectPool<TestObject>(factory,poolConfig);
    }

}
