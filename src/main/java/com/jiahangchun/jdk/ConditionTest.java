package com.jiahangchun.jdk;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2019/2/28 下午5:53
 **/
public class ConditionTest {
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        Condition condition=lock.newCondition();
        condition.await();
        lock.unlock();
    }
}
