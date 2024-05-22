package com.fgq.demo.listener;

import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 事件监听配置
 */
@Configuration
@RequiredArgsConstructor
public class FlowableEventConfig2 {

    private final MyEventListener myEventListener;
    private final MyEventListener2 myEventListener2;

    private final SpringProcessEngineConfiguration configuration;

    @PostConstruct
    public void init() {
        FlowableEventDispatcher dispatcher = configuration.getEventDispatcher();
        dispatcher.addEventListener(myEventListener,
                FlowableEngineEventType.PROCESS_CREATED,
                FlowableEngineEventType.PROCESS_COMPLETED);
        dispatcher.addEventListener(myEventListener2,
                FlowableEngineEventType.TASK_CREATED,
                FlowableEngineEventType.TASK_COMPLETED);
    }

}
