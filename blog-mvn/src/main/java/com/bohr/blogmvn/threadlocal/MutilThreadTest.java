package com.bohr.blogmvn.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Bohr Fu
 * @date 2020/9/15 13:02
 * @Version 1.0
 */
public class MutilThreadTest {

//    public static void main(String[] args) {
//        ExecutorService service = Executors.newFixedThreadPool(20);
//
//        for (int i = 0; i < 20; i++) {
//            int num = i;
//            service.execute(()->{
//                System.out.println(num + " " +  NumUtil.add10(num));
//            });
//        }
//        service.shutdown();
//    }


    //private static volatile int a = 0;
    /**
     * volatile 只能保证可见性和有序性 不能保证原子性
     *
     */
    private static AtomicInteger a = new AtomicInteger(0);


    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        // 定义五个线程 每个线程加10
        for (int i = 0 ;  i < 5 ; i++){
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0 ; j < 10 ; j++){
                        //System.out.println(a++);
                        System.out.println(a.incrementAndGet());
                        Thread.sleep(250);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }
    }
}
