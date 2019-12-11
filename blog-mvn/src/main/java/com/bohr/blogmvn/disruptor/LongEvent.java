package com.bohr.blogmvn.disruptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件
 *  生产者 消费者模式中传递的数据单元
 * @author Bohr Fu
 * @date 2019/12/11 13:57
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongEvent {

    /**
     * 简化模型只一个id
     */
    private Long eventId;
}


