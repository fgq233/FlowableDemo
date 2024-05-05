package com.fgq.demo.base;

import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
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
     * 查询历史活动
     */
    @Test
    void createHistoricActivityInstanceQuery() {
        String processInstanceId = "e9d03ce0-ffd1-11ee-8863-00ff306296e3";
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityName() + " --- " + activity.getAssignee() + " --- " + activity.getEndTime());
        }
    }


}
