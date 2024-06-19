package com.example.jobscheduler.task;

import com.example.jobscheduler.context.ExecutionContext;

public class OneTimeTask extends ScheduledTask{
    private final long executionTime;

    public OneTimeTask(ExecutionContext executionContext, long executionTime) {
        super(executionContext);
        this.executionTime = executionTime;
    }

    @Override
    public boolean isRecurring() {
        return false;
    }

    @Override
    public long getNextExecutionTime() {
        return executionTime;
    }

    @Override
    public ScheduledTask nextScheduledTask() {
        return null;
    }
}
