package com.example.jobscheduler.store;

import com.example.jobscheduler.task.ScheduledTask;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTaskStore implements TaskStore{
    private final PriorityBlockingQueue<ScheduledTask> taskQueue;
    private final Set<ScheduledTask> tasks;

    public PriorityBlockingQueueTaskStore(Comparator<ScheduledTask> comparator, int queueSize) {
        this.taskQueue = new PriorityBlockingQueue<>(queueSize, comparator);
        this.tasks = new HashSet<>();
    }

    @Override
    public ScheduledTask peek() {
        return taskQueue.peek();
    }

    @Override
    public ScheduledTask poll() {
        return taskQueue.poll();
    }

    @Override
    public void add(ScheduledTask task) {
        taskQueue.add(task);
        tasks.add(task);
    }

    @Override
    public boolean remove(ScheduledTask task) {
        boolean removed = taskQueue.remove(task);
        if (removed) {
            tasks.remove(task);
        }
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }
}
