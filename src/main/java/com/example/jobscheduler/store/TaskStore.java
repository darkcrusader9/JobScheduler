package com.example.jobscheduler.store;

import com.example.jobscheduler.task.ScheduledTask;

public interface TaskStore <T extends ScheduledTask>{
    T peek();
    T poll();
    void add(T task);
    boolean remove(T task);
    boolean isEmpty();
}
