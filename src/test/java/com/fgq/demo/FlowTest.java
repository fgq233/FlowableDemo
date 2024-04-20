package com.fgq.demo;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 流程部署
     */
    @Test
    void deployFlow() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("xml/X1.bpmn20.xml")
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
        // 删除部署的流程
        // repositoryService.deleteDeployment(deploy.getId());
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("X1");
        System.out.println("ProcessInstanceId:" + processInstance.getProcessInstanceId());
    }

    /**
     * 查询流程任务
     */
    @Test
    void queryTask() {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionId("X1:1:ad3301c4-f968-11ee-ab3f-00ff306296e3")
                .taskAssignee("B")
                .list();
        for (Task task : list) {
            System.out.println("任务id：" + task.getId());
            System.out.println("环节名称：" + task.getName());
        }
    }

    /**
     * 完成任务
     */
    @Test
    void completeTask() {
        String taskId = "b06793d2-f972-11ee-b59b-00ff306296e3";
        taskService.complete(taskId);
    }

    /**
     * 流程的挂起、激活
     */
    @Test
    void suspendedDef() {
        String processDefId = "X1:1:ad3301c4-f968-11ee-ab3f-00ff306296e3";
        // 查询流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefId)
                .singleResult();
        // 获取流程定义的状态
        boolean suspended = processDefinition.isSuspended();
        System.out.println("流程当前状态：" + (suspended ? "挂起" : "激活"));
        if (suspended) {
            System.out.println("激活流程");
            repositoryService.activateProcessDefinitionById(processDefId);
        } else {
            System.out.println("挂起流程");
            repositoryService.suspendProcessDefinitionById(processDefId);
        }
    }

    /**
     * 流程实例的挂起、激活
     */
    @Test
    void suspendedInstance() {
        String processInstanceId = "38ab1ceb-f972-11ee-ad6b-00ff306296e3";
        // 挂起流程实例
        runtimeService.suspendProcessInstanceById(processInstanceId);
        // 激活流程实例
//        runtimeService.activateProcessInstanceById(processInstanceId);
    }

}
