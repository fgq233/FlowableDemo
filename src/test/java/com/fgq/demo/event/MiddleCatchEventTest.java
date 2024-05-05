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
 * 中间捕获事件
 */
@SpringBootTest
public class MiddleCatchEventTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 中间定时器捕获事件
     */
    @Test
    void deployFlow1() {
        repositoryService.createDeployment()
                .addClasspathResource("xml/TimerCatchEvent.bpmn20.xml")
                .name("中间定时器捕获事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("TimerCatchEvent");
    }

    /**
     * 中间消息捕获事件
     */
    @Test
    void deployFlow2() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/MsgCatchEvent.bpmn20.xml")
                .name("中间消息捕获事件")
                .deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MsgCatchEvent");
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .messageEventSubscriptionName("中间消息")
                .singleResult();
        runtimeService.messageEventReceived("中间消息", execution.getId());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 中间信号捕获事件
     */
    @Test
    void deployFlow3() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/SignalCatchEvent.bpmn20.xml")
                .name("中间消息捕获事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("SignalCatchEvent");
        runtimeService.startProcessInstanceByKey("SignalCatchEvent");
        Thread.sleep(10000);
        runtimeService.signalEventReceived("中间信号");
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    void completeTask()  {
        taskService.complete("c81ff18f-0acb-11ef-a2bf-00ff306296e3");
    }
}
