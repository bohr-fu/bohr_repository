package com.bohr.blogmvn.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.AllArgsConstructor;

/**
 * @author Bohr Fu
 * @date 2019/12/11 15:21
 * @Version 1.0
 */
@AllArgsConstructor
public class Producer {

    /**
     * 单生产者
     */
    private final RingBuffer<LongEvent> ringBuffer;


    public void onData(Long data){
        long sequence = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(sequence);
            event.setEventId(data);
        }finally {
            ringBuffer.publish(sequence);
        }
    }


}
