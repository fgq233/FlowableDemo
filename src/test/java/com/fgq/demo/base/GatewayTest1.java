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
 * 排他网关
 */
@SpringBootTest
public class GatewayTest1 {


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
                .addClasspathResource("xml/Gateway1.bpmn20.xml")
                .name("排他网关案例")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        runtimeService.startProcessInstanceByKey("X5");
    }

    /**
     * 完成任务 - 网关变量
     */
    @Test
    void complete1() {
        Map<String, Object> var = new HashMap<>();
        var.put("num", 3);
        taskService.complete("fe523580-ffc3-11ee-81b9-00ff306296e3", var);
    }


    /**
     * 完成任务
     */
    @Test
    void complete2() {
        taskService.complete("30d878f1-ffc4-11ee-946b-00ff306296e3");
    }



}
