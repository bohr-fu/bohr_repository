package com.bohr.blogmvn.disruptor.consumer;

import com.bohr.blogmvn.disruptor.LongEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;


/**
 * 事件消费者
 * 同时实现了EventHandler（单消费者）和WorkHandler（多消费者）两个接口
 * 数值+10
 * @author Bohr Fu
 * @date 2019/12/11 14:17
 * @Version 1.0
 */
public class C11EventHandler implements EventHandler<LongEvent>, WorkHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        long number = event.getEventId();
        number += 10;
        System.out.println(System.currentTimeMillis()+": c1-1 consumer finished.number=" + number);
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        long number = event.getEventId();
        number += 10;
        System.out.println(System.currentTimeMillis()+": c1-1 consumer finished.number=" + number);
    }
}
