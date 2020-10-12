package com.bohr.blogmvn.threadlocal;

/**
 * Java通过ThreadLocal来实现每个线程都拥有一份自己的共享变量的拷贝。
 *
 * 使用场景：
 * 1.   用于单例实例的共享（每个线程都操作自己那一份拷贝 例如 数据库连接、Session管理等）
 * 2.	实现线程级别的数据传递（例如 在日志中请求的记录执行链路）
 *
 * @author Bohr Fu
 * @date 2020/9/15 12:00
 * @Version 1.0
 */
public class ThreadLocalTest {

    private static ThreadLocal<String> nameInfo = new ThreadLocal<>();
    private static ThreadLocal<Integer> ageInfo = new ThreadLocal<>();
    /**
     * InheritableThreadLocal 可遗传 子线程可以获取主线程的值 ThreadLocal则不支持
     */
    private static ThreadLocal<Integer> count = new InheritableThreadLocal<>();

    public static void setInfo(String name, Integer age) {
        nameInfo.set(name);
        ageInfo.set(age);
    }

    public static String getInfo() {
        return nameInfo.get() + ";" + ageInfo.get() + ";" + count.get();
    }

    public static void modifyCount(Integer mofify){
        count.set(count.get() + mofify);
    }

    /**
     * 可以把ThreadLocal<T>简单的理解成Map<Thread,T>。
     * ThreadLocal提供了get和set等方法，get方法总是返回当前线程调用set方法时设置的最新值。
     * 如果是第一次调用get方法，将会返回initialValue方法里面的设置的初始值。
     * @param args
     */
    public static void main(String[] args) {
        ageInfo.set(16);
        count.set(100);

        new Thread(() -> {
            // null;null;100
            System.out.println(ThreadLocalTest.getInfo());
            ThreadLocalTest.setInfo("张三",16);
            ThreadLocalTest.modifyCount(50);
            // 张三;16;150
            System.out.println(ThreadLocalTest.getInfo());
        },"Thread01").start();

        new Thread(() -> {
            System.out.println(ThreadLocalTest.getInfo());
            ThreadLocalTest.setInfo("李四",16);
            ThreadLocalTest.modifyCount(20);
            // 李四;16;120
            System.out.println(ThreadLocalTest.getInfo());
        },"Thread02").start();

        nameInfo.remove();
        ageInfo.remove();
        count.remove();

    }

}
