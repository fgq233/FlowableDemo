package com.fgq.demo;

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
public class GatewayTest2 {


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
                .addClasspathResource("xml/Gateway2.bpmn20.xml")
                .name("并行网关案例")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        runtimeService.startProcessInstanceByKey("X6");
    }


    /**
     * 完成任务
     */
    @Test
    void complete() {
        taskService.complete("16fda259-ffd2-11ee-baf0-00ff306296e3");
    }



}
