package com.bohr.blogmvn.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 *
 * 事件工厂
 * @author Bohr Fu
 * @date 2019/12/11 14:00
 * @Version 1.0
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    /**
     * 实例化方法
     * @return
     */
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
