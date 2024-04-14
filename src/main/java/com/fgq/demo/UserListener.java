package com.fgq.demo;


import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

public class UserListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        if (EVENTNAME_CREATE.equals(delegateTask.getName())) {
            // 任务节点的创建时，指定处理人
            delegateTask.setAssignee("DDD");
        }
    }
}
