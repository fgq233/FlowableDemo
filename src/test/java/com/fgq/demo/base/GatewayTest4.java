package com.fgq.demo.base;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 包容网关
 */
@SpringBootTest
public class GatewayTest4 {


    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;

    /**
     * 流程部署
     */
    @Test
    void deployFlow() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("xml/Gateway4.bpmn20.xml")
                .name("事件网关案例")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        runtimeService.startProcessInstanceByKey("Gateway4");
    }



    /**
     * 发送信号
     */
    @Test
    void signalEventReceived() throws InterruptedException {
//        runtimeService.signalEventReceived("事件信号");
        Thread.sleep(Integer.MAX_VALUE);
    }



}
