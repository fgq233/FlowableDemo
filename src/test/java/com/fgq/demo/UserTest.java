package com.fgq.demo;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分配用户测试
 */
@SpringBootTest
public class UserTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    /**
     * 流程部署
     */
    @Test
    void deployFlow() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("xml/X2.bpmn20.xml")
                .name("分配用户测试流程")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("X2");
        System.out.println("ProcessInstanceId:" + processInstance.getProcessInstanceId());
    }

    /**
     * 查询流程任务
     */
    @Test
    void queryTask() {
        List<Task> list = taskService.createTaskQuery().taskAssignee("AAA").list();
        for (Task task : list) {
            System.out.println("任务id：" + task.getId());
            System.out.println("环节名称：" + task.getName());
        }
    }

    /**
     * AAA 完成任务
     */
    @Test
    void completeTaskA() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "BBB");
        taskService.complete("42eec25e-fa5d-11ee-8302-00ff306296e3", variables);
    }


    /**
     * BBB 完成任务
     */
    @Test
    void completeTaskB() {
        taskService.complete("5ee7dccf-fa5d-11ee-ada6-00ff306296e3");
    }

    /**
     * CCC 完成任务
     */
    @Test
    void completeTaskC() {
        taskService.complete("7fa387dc-fa5d-11ee-8c2f-00ff306296e3");
    }

    /**
     * DDD 完成任务
     */
    @Test
    void completeTaskD() {
        taskService.complete("dd79236d-fa5d-11ee-9e10-00ff306296e3");
    }

}
