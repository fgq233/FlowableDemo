package com.fgq.demo.delegate;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class ErrorDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("异常服务任务ErrorDelegate：" + LocalDateTime.now());
        // 业务执行发现有异常，手动抛出BpmnError
        // 此处的 errorCode 需要和 BPMN.xml 中定义的errorCode一致
        throw new BpmnError("404");
    }

}
