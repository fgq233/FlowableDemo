package com.fgq.demo.event;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 启动事件
 */
@SpringBootTest
public class StartEventTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 定时器启动事件
     */
    @Test
    void deployFlow1() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/StartTimerEvent.bpmn20.xml")
                .name("定时器启动事件")
                .deploy();
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 消息启动事件
     */
    @Test
    void deployFlow2() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/StartMessageEvent.bpmn20.xml")
                .name("消息启动事件")
                .deploy();
        runtimeService.startProcessInstanceByMessage("启动消息");
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 信号启动事件
     */
    @Test
    void deployFlow3() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/StartSignalEvent.bpmn20.xml")
                .name("信号启动事件")
                .deploy();
        // 发送信号
        runtimeService.signalEventReceived("启动信号");
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 错误启动事件
     */
    @Test
    void deployFlow4() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/StartErrorEvent.bpmn20.xml")
                .name("错误启动事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("StartErrorEvent");
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 定时器捕获中间事件
     */
//    @Test
//    void deployFlow2() throws InterruptedException {
//        repositoryService.createDeployment()
//                .addClasspathResource("xml/TimerEvent2.bpmn20.xml")
//                .name("定时器捕获中间事件")
//                .deploy();
//        runtimeService.startProcessInstanceByKey("TimerEvent2");
//        Thread.sleep(Integer.MAX_VALUE);
//    }


}
