package com.fgq.demo.event;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 启动事件
 */
@SpringBootTest
public class BoundEventTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 边界定时器事件(中断)
     */
    @Test
    void deployFlow1() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundTimerEvent1.bpmn20.xml")
                .name("边界定时器事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundTimerEvent1");
        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 边界定时器事件(不中断)
     */
    @Test
    void deployFlow2() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundTimerEvent2.bpmn20.xml")
                .name("边界定时器事件2")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundTimerEvent2");
        Thread.sleep(Integer.MAX_VALUE);
//        taskService.complete("bb246510-0aa9-11ef-b77b-00ff306296e3");
    }

    /**
     * 边界消息事件(中断任务)
     */
    @Test
    void deployFlow3() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundMessageEvent1.bpmn20.xml")
                .name("边界消息事件")
                .deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("BoundMessageEvent1");

        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .messageEventSubscriptionName("边界消息")
                .singleResult();
        runtimeService.messageEventReceived("边界消息", execution.getId());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 边界消息事件(不中断任务)
     */
    @Test
    void deployFlow4() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundMessageEvent2.bpmn20.xml")
                .name("边界消息事件2")
                .deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("BoundMessageEvent2");

        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .messageEventSubscriptionName("边界消息")
                .singleResult();
        runtimeService.messageEventReceived("边界消息", execution.getId());
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 边界信号事件(中断任务)
     */
    @Test
    void deployFlow5() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundSignalEvent.bpmn20.xml")
                .name("边界信号事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundSignalEvent");
        runtimeService.startProcessInstanceByKey("BoundSignalEvent");

//        runtimeService.signalEventReceived("边界信号");
//        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 边界错误事件
     */
    @Test
    void deployFlow6() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundErrorEvent.bpmn20.xml")
                .name("边界错误事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundErrorEvent");
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 边界错误事件（嵌套子流程)
     */
    @Test
    void deployFlow7() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundErrorEvent2.bpmn20.xml")
                .name("边界错误事件2")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundErrorEvent2");
        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 边界补偿事件
     */
    @Test
    void deployFlow8()  {
        repositoryService.createDeployment()
                .addClasspathResource("xml/BoundCompensationEvent.bpmn20.xml")
                .name("边界补偿事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("BoundCompensationEvent");
    }

    @Test
    void completeTask() throws InterruptedException {
        taskService.complete("c81ff18f-0acb-11ef-a2bf-00ff306296e3");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
