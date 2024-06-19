package com.example.jobscheduler.runner;

import com.example.jobscheduler.store.TaskStore;
import com.example.jobscheduler.task.ScheduledTask;

public class TaskRunner implements Runnable{
    final TaskStore<ScheduledTask> taskStore;
    private boolean running = true;

    public TaskRunner(TaskStore<ScheduledTask> taskStore) {
        this.taskStore = taskStore;
    }

    @Override
    public void run() {
        ScheduledTask task = null;
        synchronized (taskStore) {
            while (taskStore.isEmpty()){
                try{
                    taskStore.wait();
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            long currentTime = System.currentTimeMillis();
            ScheduledTask nextTask = taskStore.peek();
            if(nextTask !=null && nextTask.getNextExecutionTime() <= currentTime){
                task = taskStore.poll();
            } else {
                try{
                    taskStore.wait(nextTask.getNextExecutionTime() - currentTime);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if(task != null){
                try {
                    System.out.println("Starting task: " + task + " at " + System.currentTimeMillis());
                    task.execute();
                    System.out.println("Completed task: " + task + " at " + System.currentTimeMillis());
                    if (task.isRecurring()) {
                        taskStore.add(task.nextScheduledTask());
                    }
                } catch (Exception e) {
                    System.err.println("Error executing task: " + e.getMessage());
                }
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
