package com.fgq.demo.base;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Spring 环境
 */
@SpringBootTest
public class FlowTest {

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
                .addClasspathResource("xml/X.bpmn20.xml")
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 删除部署的流程
     */
    @Test
    void deleteDeploy() {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("X").singleResult();
        repositoryService.deleteDeployment(definition.getDeploymentId(), true);
//        repositoryService.deleteDeployment(definition.getDeploymentId());
    }


    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("X");
        System.out.println("ProcessInstanceId:" + processInstance.getProcessInstanceId());
    }

    /**
     * 查询流程任务
     */
    @Test
    void queryTask() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("A")
                .list();
        for (Task task : list) {
            System.out.println("任务id：" + task.getId());
            System.out.println("主键ID：" + task.getTaskDefinitionKey());
            System.out.println("环节名称：" + task.getName());
            System.out.println("办理人：" + task.getAssignee());
            System.out.println("描述信息：" + task.getDescription());
        }
    }


    /**
     * 完成任务
     */
    @Test
    void completeTask() throws InterruptedException {
        Task task = taskService.createTaskQuery().taskAssignee("user3").singleResult();
        if (task != null) {
            taskService.complete(task.getId());
        }
//        Thread.sleep(10000L);
    }


}
