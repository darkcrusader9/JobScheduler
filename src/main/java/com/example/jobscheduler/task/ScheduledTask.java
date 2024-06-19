package com.example.jobscheduler.task;

import com.example.jobscheduler.context.ExecutionContext;

public abstract class ScheduledTask {
    ExecutionContext executionContext;

    public ScheduledTask(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public abstract boolean isRecurring();
    public abstract long getNextExecutionTime();
    public abstract ScheduledTask nextScheduledTask();
    public void execute(){
        executionContext.execute();
    }
}
