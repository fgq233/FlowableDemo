package com.fgq.demo.base;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class SuspendTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;


    /**
     * 流程定义的挂起、激活
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

