package com.fgq.demo.listener;

import lombok.AllArgsConstructor;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.RuntimeService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 事件监听配置
 */
@AllArgsConstructor
@Configuration
public class FlowableEventConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final MyEventListener myEventListener;
    private final MyEventListener2 myEventListener2;
    private final RuntimeService runtimeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        runtimeService.addEventListener(myEventListener,
                FlowableEngineEventType.PROCESS_CREATED,
                FlowableEngineEventType.PROCESS_COMPLETED);
        runtimeService.addEventListener(myEventListener2,
                FlowableEngineEventType.TASK_CREATED,
                FlowableEngineEventType.TASK_COMPLETED);
    }

}
