package com.fgq.demo.listener;


import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

public class UserListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("触发UserListener");
        if (EVENTNAME_CREATE.equals(delegateTask.getEventName())) {
            System.out.println("任务节点创建事件触发，指定处理人");
            delegateTask.setAssignee("DDD");
        }
    }
}
