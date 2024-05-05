package com.fgq.demo.event;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 结束事件
 */
@SpringBootTest
public class EndEventTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;



    /**
     * 结束错误事件
     */
    @Test
    void deployFlow1() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/EndErrorEvent.bpmn20.xml")
                .name("结束错误事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("EndErrorEvent");
    }

    /**
     * 结束终止事件
     */
    @Test
    void deployFlow2() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("xml/EndTerminateEvent.bpmn20.xml")
                .name("结束终止事件")
                .deploy();
        runtimeService.startProcessInstanceByKey("EndTerminateEvent");
    }

    @Test
    void completeTask() throws InterruptedException {
        Map<String, Object> var  = new HashMap<>();
        var.put("num", 80);
        taskService.complete("0f172c07-0ae5-11ef-acb2-00ff306296e3", var);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
