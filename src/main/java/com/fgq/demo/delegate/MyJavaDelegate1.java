package com.fgq.demo.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.time.LocalDateTime;

public class MyJavaDelegate1 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("自动服务1：" + LocalDateTime.now());
    }

}
