package com.bohr.blogmvn.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * 事件转化类
 * 将SourceEventModel转换成TargetEventModel，然后disruptor中的handler链就消费TargetEventModel对象。
 * 简化处理 选择实现单参数的转化接口
 * @author Bohr Fu
 * @date 2019/12/11 14:10
 * @Version 1.0
 */
public class LongEventTranslator implements EventTranslatorOneArg<LongEvent,Long> {
    /**
     * 转换方法
     * @param event 事件
     * @param sequence 处理的序号
     * @param argu 转化参数
     */
    @Override
    public void translateTo(LongEvent event, long sequence, Long argu) {
        event.setEventId(argu);
    }

}
