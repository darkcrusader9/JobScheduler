package com.example.jobscheduler.context;

public class PrintTask implements ExecutionContext {
    private final String message;

    public PrintTask(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Executing task: " + message + " at " + System.currentTimeMillis());
    }
}