package com.bohr.blogmvn.threadlocal;

import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Bohr Fu
 * @date 2020/9/15 13:01
 * @Version 1.0
 */
public class NumUtil {
    public static int addNum = 0;

    public static int add10(int num) {
        addNum = num;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return addNum + 10;
    }

}
