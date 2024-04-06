package com.fgq.demo;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DeplyTest2 {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    void deployFlow(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("xml/flow1.bpmn20.xml")
                .name("第一个流程案例")
                .deploy();
        System.out.println(deploy.getId());
    }


}
