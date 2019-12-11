package com.bohr.blogmvn.disruptor;

import com.bohr.blogmvn.disruptor.consumer.C11EventHandler;
import com.bohr.blogmvn.disruptor.consumer.C12EventHandler;
import com.bohr.blogmvn.disruptor.consumer.C21EventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 *
 * disruptor测试类
 * @author Bohr Fu
 * @date 2019/12/11 14:34
 * @Version 1.0
 */
public class Main {

    /**
     * 在disruptor框架调用start方法之前，往往需要将消息的消费者指定给disruptor框架。
     *
     * 常用的方法是：disruptor.handleEventsWith(EventHandler ... handlers)，将多个EventHandler的实现类传入方法，封装成一个EventHandlerGroup，实现多消费者消费。
     *
     * disruptor的另一个方法是：disruptor.handleEventsWithWorkerPool(WorkHandler ... handlers)，将多个WorkHandler的实现类传入方法，封装成一个EventHandlerGroup实现多消费者消费。
     *
     * 两者共同点都是，将多个消费者封装到一起，供框架消费消息。
     *
     * 不同点在于，
     *
     * 1. 对于某一条消息m，handleEventsWith方法返回的EventHandlerGroup，Group中的每个消费者都会对m进行消费，各个消费者之间不存在竞争。handleEventsWithWorkerPool方法返回的EventHandlerGroup，Group的消费者对于同一条消息m不重复消费；也就是，如果c0消费了消息m，则c1不再消费消息m。
     *
     * 2. 传入的形参不同。对于独立消费的消费者，应当实现EventHandler接口。对于不重复消费的消费者，应当实现WorkHandler接口。
     *
     * 因此，根据消费者集合是否独立消费消息，可以对不同的接口进行实现。也可以对两种接口同时实现，具体消费流程由disruptor的方法调用决定。
     * @param argus
     */
    public static void main(String[] argus){
        //环形队列长度，必须是2的N次方
        int bufferSize = 1024*1024;

        LongEventFactory longEventFactory = new LongEventFactory();

        // 定义disruptor单生产者 阻塞策略
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                longEventFactory,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                /**
                 * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现；
                 * SleepingWaitStrategy 的性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
                 * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性。
                 */
                new BlockingWaitStrategy());

        // 并行计算实现,c1,c2互相不依赖
        //disruptor.handleEventsWith(new C11EventHandler(),new C21EventHandler());
        // 串行计算实现
        //disruptor.handleEventsWith(new C11EventHandler()).then(new C21EventHandler());

        // 菱形
        //disruptor.handleEventsWith(new C11EventHandler(),new C12EventHandler()).then(new C21EventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 简单的事件转化可以用lamda表达式 省略转化类
        ringBuffer.publishEvent(((event, l, o) -> event.setEventId(o)),10L);
        //ringBuffer.publishEvent(new LongEventTranslator(),100L);

        disruptor.shutdown();

    }
}
