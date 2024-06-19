package com.example.jobscheduler;

import com.example.jobscheduler.context.ExecutionContext;
import com.example.jobscheduler.context.PrintTask;
import com.example.jobscheduler.store.PriorityBlockingQueueTaskStore;
import com.example.jobscheduler.store.TaskStore;
import com.example.jobscheduler.task.OneTimeTask;
import com.example.jobscheduler.task.RecurringTask;
import com.example.jobscheduler.task.ScheduledTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.TaskScheduler;

import java.util.Comparator;

@SpringBootApplication
public class JobSchedulerApplication {

    public static void main(String[] args) {
        // Configure the number of worker threads
        ExecutorConfig executorConfig = new ExecutorConfig(3);

        // Define a comparator to order tasks by their next execution time
        Comparator<ScheduledTask> comparator = Comparator.comparingLong(ScheduledTask::getNextExecutionTime);

        // Create a task store with a priority blocking queue
        TaskStore<ScheduledTask> taskStore = new PriorityBlockingQueueTaskStore(comparator, 10);

        // Create a TaskScheduler instance
        JobScheduler jobScheduler = new JobScheduler(executorConfig, taskStore);

        // Define a simple ExecutionContext implementation for testing


        // Add one-time tasks to the task store
        jobScheduler.scheduleTask(new OneTimeTask(new PrintTask("One-time task 1"), System.currentTimeMillis()));
        jobScheduler.scheduleTask(new OneTimeTask(new PrintTask("One-time task 2"), System.currentTimeMillis() + 1000));

        // Add recurring tasks to the task store
        jobScheduler.scheduleTask(new RecurringTask(new PrintTask("Recurring task 1"), System.currentTimeMillis() + 1000, 3000));
        jobScheduler.scheduleTask(new RecurringTask(new PrintTask("Recurring task 2"), System.currentTimeMillis() + 2000, 5000));

        // Let the scheduler run for a while to observe task execution
        try {
            Thread.sleep(15000);  // Run for 15 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stop the task scheduler
        jobScheduler.stop();

        // Print a message indicating the scheduler has been stopped
        System.out.println("Task Scheduler has been stopped.");
        //SpringApplication.run(JobSchedulerApplication.class, args);
    }

}
