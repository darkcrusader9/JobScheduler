package com.example.jobscheduler.task;

import com.example.jobscheduler.context.ExecutionContext;

public class RecurringTask extends ScheduledTask{
    private final long executionTime;
    private final long interval;

    public RecurringTask(ExecutionContext executionContext, long executionTime, long interval) {
        super(executionContext);
        this.executionTime = executionTime;
        this.interval = interval;
    }

    @Override
    public boolean isRecurring() {
        return true;
    }

    @Override
    public long getNextExecutionTime() {
        return executionTime + interval;
    }

    @Override
    public ScheduledTask nextScheduledTask() {
        return new RecurringTask(executionContext, getNextExecutionTime(), interval);
    }
}
