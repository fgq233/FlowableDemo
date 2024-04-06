package com.fgq.demo;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DeplyTest1 {

    @Test
    void deployFlow() {
        // 1.流程引擎的配置
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable?serverTimezone=UTC&nullCatalogMeansCurrent=true")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 2.构建流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();
        // 3. 部署流程
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("xml/flow1.bpmn20.xml")
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
    }


}
