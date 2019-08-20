package com.lr.common.model;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 对象池
 *
 * @author shijie.xu
 * @since 2019年08月20日
 */
public class ObjectPool<T, R> {
    private final List<T> pool;
    private final Semaphore sem;

    ObjectPool(int size, T t) {
        pool = new Vector<T>() {
        };
        for(int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    R execute(Function<T, R> function) {
        T t = null;
        try {
            sem.acquire();
            t = pool.remove(0);
            System.out.println("3");
            return function.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("4");
            pool.add(t);
            sem.release();
        }
        System.out.println("end");
        return function.apply(null);
    }

}
