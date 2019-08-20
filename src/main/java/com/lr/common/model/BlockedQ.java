package com.lr.common.model;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
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

    private int size;

    private BlockedQ(int s) {
        this.size = s;
    }

    static <T> BlockedQ<T> getIns() {
        return getIns(16);
    }

    static <T> BlockedQ<T> getIns(int size) {
        if(blockedQ == null) {
            synchronized(BlockedQ.class) {
                if(blockedQ == null) {
                    blockedQ = new BlockedQ<>(size);
                }
            }
        }
        return blockedQ;
    }

    public void doPush(T num) {
        if(lock.tryLock()) {
            try {
                while(queue.size() >= size) {
                    System.out.println(Thread.currentThread().getName() + ": no available space waiting!");
                    notFull.await();
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
    }

    public T doPoll() {
        T res = null;
        if(lock.tryLock()) {
            try {
                while(queue.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + ": no data waiting!");
                    notEmpty.await();
                }
                res = queue.poll();
                System.out.println(Thread.currentThread().getName() + " poll:" + res);
                notFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return res;
    }

    public T doPoll(long millis) {
        T res = null;
        long start = System.currentTimeMillis();
        if(lock.tryLock()) {
            try {
                while(queue.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + ": no data waiting!");
                    notEmpty.await(millis, TimeUnit.MILLISECONDS);
                    long cur = System.currentTimeMillis();
                    if(!queue.isEmpty() || cur - start > millis) {
                        break;
                    }
                }
                if(queue.isEmpty()) {
                    return res;
                }
                res = queue.poll();
                System.out.println(Thread.currentThread().getName() + " poll:" + res);
                notFull.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return res;
    }
}
