package com.example.jobscheduler;

import com.example.jobscheduler.runner.TaskRunner;
import com.example.jobscheduler.store.TaskStore;
import com.example.jobscheduler.task.ScheduledTask;

import java.util.ArrayList;
import java.util.List;

public class JobScheduler {
    private final List<Thread> threads;
    private final TaskStore<ScheduledTask> taskStore;

    public JobScheduler(ExecutorConfig executorConfig, TaskStore<ScheduledTask> taskStore) {
        this.threads = new ArrayList<>();
        this.taskStore = taskStore;
        for (int i = 0; i < executorConfig.getNumThreads(); i++) {
            Thread thread = new Thread(new TaskRunner(taskStore));
            thread.start();
            threads.add(thread);
        }
    }

    public void stop() {
        for (Thread thread : threads) {
             thread.stop();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        synchronized (taskStore) {
            taskStore.notifyAll();
        }
    }

    public void scheduleTask(ScheduledTask task) {
        synchronized (taskStore) {
            taskStore.add(task);
            taskStore.notify();
        }
    }

    public boolean cancelTask(ScheduledTask task) {
        synchronized (taskStore) {
            boolean removed = taskStore.remove(task);
            taskStore.notify();
            return removed;
        }
    }

    public void setNumThreads(int numThreads) {
        int currentThreads = threads.size();
        if (numThreads > currentThreads) {
            for (int i = currentThreads; i < numThreads; i++) {
                Thread thread = new Thread(new TaskRunner(taskStore));
                thread.start();
                threads.add(thread);
            }
        } else if (numThreads < currentThreads) {
            for (int i = currentThreads - 1; i >= numThreads; i--) {
                (threads.get(i)).stop();
                threads.remove(i);
            }
        }
    }
}
