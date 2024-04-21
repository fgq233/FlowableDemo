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
public class GatewayTest3 {


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
                .addClasspathResource("xml/Gateway3.bpmn20.xml")
                .name("包容网关案例")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        runtimeService.startProcessInstanceByKey("X7");
    }


    /**
     * 完成任务 - 网关变量
     */
    @Test
    void complete1() {
        Map<String, Object> var = new HashMap<>();
        var.put("money", 888);
        taskService.complete("602371f0-ffd0-11ee-8a89-00ff306296e3", var);
    }


    /**
     * 完成任务
     */
    @Test
    void complete2() {
        taskService.complete("4052f4b9-ffd1-11ee-bcd3-00ff306296e3");
    }



}
