package com.fgq.demo.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.springframework.stereotype.Component;

import static org.flowable.common.engine.api.delegate.event.FlowableEngineEventType.JOB_EXECUTION_FAILURE;
import static org.flowable.common.engine.api.delegate.event.FlowableEngineEventType.JOB_EXECUTION_SUCCESS;

@Component
public class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        FlowableEventType type = event.getType();
        if (JOB_EXECUTION_SUCCESS.equals(type)) {
            System.out.println("A job well done!");
        } else if (JOB_EXECUTION_FAILURE.equals(type)) {
            System.out.println("A job has failed...");
        } else {
            System.out.println("Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
