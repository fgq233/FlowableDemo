package com.fgq.demo.base;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 候选人、 候选组
 */
@SpringBootTest
public class CandidateTest {

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
                .addClasspathResource("xml/X4.bpmn20.xml")
                .name("候选人候选组案例")
                .deploy();
    }

    /**
     * 启动流程实例
     */
    @Test
    void startProcessInstance() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("candidateUser1", "A");
        variables.put("candidateUser2", "B");
        runtimeService.startProcessInstanceByKey("X4", variables);
    }

    /**
     * 候选人查询流程任务、拾取任务
     */
    @Test
    void queryTask() {
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser("A")
                .list();
        for (Task task : list) {
            System.out.println("任务id：" + task.getId());
            System.out.println("环节名称：" + task.getName());
            // 拾取任务
            taskService.claim(task.getId(), "A");
            System.out.println("任务拾取成功");
        }
    }

    /**
     * 候选人归还任务
     */
    @Test
    void completeTask1() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("A")
                .singleResult();
        if (task != null) {
            taskService.unclaim(task.getId());
            System.out.println("归还任务成功");
        }
    }

    /**
     * 交接任务
     */
    @Test
    void setAssignee() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("A")
                .singleResult();
        if (task != null) {
            taskService.delegateTask(task.getId(), "B");
//            taskService.setAssignee(task.getId(), "B");
            System.out.println("交接任务成功");
        }
    }

    /**
     * 第一环节完成任务
     */
    @Test
    void suspendedDef() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("candidateGroup", "group_1");
        taskService.complete("9c5715c2-ff30-11ee-b8fa-00ff306296e3", variables);
    }

    /**
     * 候选组中用户查询任务、拾取任务
     */
    @Test
    void taskCandidateGroup() {
        Group group = identityService.createGroupQuery().groupId("group_1").singleResult();
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateGroup(group.getId())
                .list();
        for (Task task : list) {
            System.out.println("任务id：" + task.getId());
            System.out.println("环节名称：" + task.getName());
            // 拾取任务
            taskService.claim(task.getId(), "1");
        }
    }

    /**
     * 第二环节完成任务
     */
    @Test
    void completeTask2() {
        taskService.complete("b32c9647-ff34-11ee-a870-00ff306296e3");
    }

}
