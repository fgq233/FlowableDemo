package com.fgq.demo.event;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 中间抛出事件
 */
@SpringBootTest
public class MiddleThrowEventTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;



    /**
     * 中间信号抛出事件
     */
    @Test
    void deployFlow3() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/SignalThrowMsg.bpmn20.xml")
                .name("中间信号抛出事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("SignalThrowMsg");
        Thread.sleep(10000);
    }

    @Test
    void completeTask() throws InterruptedException {
        taskService.complete("fbdc33f0-0adc-11ef-b27e-00ff306296e3"); Thread.sleep(10000);
    }
}
