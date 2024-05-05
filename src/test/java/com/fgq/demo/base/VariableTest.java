package com.fgq.demo.base;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 变量测试
 */
@SpringBootTest
public class VariableTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    /**
     * 流程部署
     */
    @Test
    void deployFlow() {
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("xml/X3.bpmn20.xml")
                .name("变量测试流程")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("key1", "A");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("X3", variables);
        System.out.println("ProcessInstanceId:" + processInstance.getProcessInstanceId());
    }

    /**
     * 设置变量
     */
    @Test
    void setVar() {
        String executionId = "35c527fb-fa68-11ee-af27-00ff306296e3";
        runtimeService.setVariable(executionId, "key2", "B");
        runtimeService.setVariableLocal(executionId, "key3", "C");

        String taskId = "35c7e71f-fa68-11ee-af27-00ff306296e3";
        taskService.setVariable(taskId, "key4", "D");
        taskService.setVariableLocal(taskId, "key5", "E");
    }

    /**
     * 获取变量
     */
    @Test
    void getVar() {
        String executionId = "35c527fb-fa68-11ee-af27-00ff306296e3";
        Map<String, Object> variables1 = runtimeService.getVariables(executionId);
        System.out.println("variables1:" + variables1);

        String taskId = "fa0c478f-fa6a-11ee-bd3f-00ff306296e3";
        Map<String, Object> variables2 = taskService.getVariables(taskId);
        System.out.println("variables2:" + variables2);
    }

    /**
     * AAA 完成任务
     */
    @Test
    void completeTaskA() {
        taskService.complete("35c7e71f-fa68-11ee-af27-00ff306296e3");
    }

    /**
     * BBB 完成任务
     */
    @Test
    void completeTaskB() {
        taskService.complete("fa0c478f-fa6a-11ee-bd3f-00ff306296e3");
    }

    /**
     * 获取历史变量
     */
    @Test
    void getVarHi() {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId("35c4d9d8-fa68-11ee-af27-00ff306296e3")
                .orderByVariableName()
                .desc()
                .list();
        for (HistoricVariableInstance instance : list) {
            System.out.println(instance.getVariableName() + ":" + instance.getValue());
        }
    }
}
