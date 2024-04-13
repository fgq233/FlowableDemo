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
        taskService.complete("e24b36ce-f9b9-11ee-ad72-00ff306296e3", variables);
    }


    /**
     * BBB 完成任务
     */
    @Test
    void completeTaskB() {
        taskService.complete("f7367018-f9b9-11ee-9fe4-00ff306296e3");
    }

    /**
     * CCC 完成任务
     */
    @Test
    void completeTaskC() {
        taskService.complete("08e87822-f9ba-11ee-933a-00ff306296e3");
    }

}
