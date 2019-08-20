package com.lr.common.model;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 请填写类注释
 *
 * @author shijie.xu
 * @since 2019年08月19日
 */
public class BlockedQ<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final ArrayDeque<T> queue = new ArrayDeque<>();

    private static volatile BlockedQ blockedQ = null;

    private BlockedQ() {
    }

    public static <T> BlockedQ<T> getIns() {
        if(blockedQ == null) {
            synchronized(BlockedQ.class) {
                if(blockedQ == null) {
                    blockedQ = new BlockedQ<>();
                }
            }
        }
        return blockedQ;
    }

    public void doPush(T num) {
        try {
            lock.lock();
            while(queue.size() >= 16) {

                System.out.println(Thread.currentThread().getName() + ": no available space waiting!");
                notFull.await();
                System.out.println("push");

            }
            queue.add(num);
            System.out.println(Thread.currentThread().getName() + " push:" + num);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T doPoll() {
        T res = null;
        try {
            lock.lock();
            while(queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + ": no data waiting!");
                notEmpty.await();
                System.out.println("poll");

            }
            res = queue.poll();
            System.out.println(Thread.currentThread().getName() + " poll:" + res);
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return res;
    }
}
