package com.bohr.blogmvn.disruptor.consumer;

import com.bohr.blogmvn.disruptor.LongEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 *
 *  乘20操作
 * @author Bohr Fu
 * @date 2019/12/11 14:30
 * @Version 1.0
 */
public class C22EventHandler implements EventHandler<LongEvent>, WorkHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long l, boolean b) throws Exception {
        long number = event.getEventId();
        number *= 20;
        System.out.println(System.currentTimeMillis()+": c2-2 consumer finished.number=" + number);
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        long number = event.getEventId();
        number *= 20;
        System.out.println(System.currentTimeMillis()+": c2-2 consumer finished.number=" + number);
    }
}
