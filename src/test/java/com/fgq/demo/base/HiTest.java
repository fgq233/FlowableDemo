package com.fgq.demo.base;

import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Spring 环境
 */
@SpringBootTest
public class HiTest {

    @Autowired
    private HistoryService historyService;

    /**
     * 查询历史流程实例
     */
    @Test
    void createHistoricProcessInstanceQuery() {
        String processInstanceId = "e9d03ce0-ffd1-11ee-8863-00ff306296e3";
        HistoricProcessInstance hiProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        System.out.println(hiProcessInstance.getId());
        System.out.println(hiProcessInstance.getName());
        System.out.println(hiProcessInstance.getEndTime());
        System.out.println(hiProcessInstance.getDurationInMillis());
    }


    /**
     * 查询历史
     */
    @Test
    void historicQuery() {
        // 查询已经完成的历史流程实例
        List<HistoricProcessInstance> instanceList = historyService.createHistoricProcessInstanceQuery()
//                .processInstanceId("")
                .finished()
                .orderByProcessInstanceEndTime()
                .desc()
                .list();
        for (HistoricProcessInstance hiProcessInstance : instanceList) {
            // 查询已经完成的历史任务实例
            List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(hiProcessInstance.getId())
                    .finished()
                    .orderByHistoricTaskInstanceEndTime()
                    .asc()
                    .list();
            taskList.forEach(hiTask -> System.out.println("环节：" + hiTask.getName() + ",办理人：" + hiTask.getAssignee() + ",完成时间：" + hiTask.getEndTime()));
        }
    }


}
