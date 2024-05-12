package com.fgq.demo.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.delegate.ActivityBehavior;

public class MyActivityBehavior implements ActivityBehavior {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("MyActivityBehavior...");
    }
}
